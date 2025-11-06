package org.example.repo;

import org.example.domain.PersonalBestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalBestRepository extends JpaRepository<PersonalBestEntity, Long> {
    List<PersonalBestEntity> findByUser_Id(Long userId);

    Optional<PersonalBestEntity> findByIdAndUser_Id(Long id, Long userId);

    Optional<PersonalBestEntity> findByUser_IdAndExercise_IdAndReps(Long userId, Long exerciseId, int reps);
}

