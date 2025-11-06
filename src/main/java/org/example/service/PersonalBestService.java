package org.example.service;

import org.example.api.dto.PersonalBestCreate;
import org.example.api.dto.PersonalBestView;
import org.example.domain.PersonalBestEntity;
import org.example.domain.UserEntity;
import org.example.repo.ExerciseRepository;
import org.example.repo.PersonalBestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PersonalBestService {
    private final PersonalBestRepository prs;
    private final ExerciseRepository exercises;

    public PersonalBestService(PersonalBestRepository p, ExerciseRepository e) {
        this.prs = p;
        this.exercises = e;
    }

    @Transactional(readOnly = true)
    public List<PersonalBestView> listForUser(Long userId) {
        return prs.findByUser_Id(userId).stream().map(e ->
                new PersonalBestView(e.getId(), e.getExercise().getId(), e.getExercise().getName(),
                        e.getReps(), e.getWeightKg(), e.getAchievedOn())
        ).toList();
    }

    @Transactional
    public PersonalBestView upsert(Long userId, PersonalBestCreate dto) {
        var ex = exercises.findById(dto.exerciseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown exercise"));

        var existing = prs.findByUser_IdAndExercise_IdAndReps(userId, ex.getId(), dto.reps());
        var entity = existing.orElseGet(PersonalBestEntity::new);
        entity.setExercise(ex);
        entity.setReps(dto.reps());
        entity.setWeightKg(dto.weightKg());
        entity.setAchievedOn(dto.achievedOn());

        var userRef = new UserEntity();
        userRef.setId(userId);
        entity.setUser(userRef);

        var saved = prs.save(entity);
        return new PersonalBestView(saved.getId(), ex.getId(), ex.getName(), saved.getReps(), saved.getWeightKg(), saved.getAchievedOn());
    }

    @Transactional
    public void delete(Long userId, Long prId) {
        var pr = prs.findByIdAndUser_Id(prId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        prs.delete(pr);
    }
}

