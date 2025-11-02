package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.PetDTO;
import org.example.entity.Pet;
import org.example.exception.PetNotFoundException;
import org.example.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository repo;

    @Transactional
    public PetDTO adopt(Pet pet) {
        return toDTO(repo.save(pet));
    }

    public List<PetDTO> listAll() {
        return repo.findAll().stream().map(this::toDTO).toList();
    }

    public PetDTO get(Long id) {
        return repo.findById(id).map(this::toDTO).orElseThrow(() -> new PetNotFoundException(id));
    }

    private PetDTO toDTO(Pet p) {
        return new PetDTO(
                p.getId(),
                p.getName(),
                p.getSpecies(),
                p.getHungerLevel(),
                p.getHappiness(),
                p.getCreatedAt(),
                p.getUpdatedAt());
    }

    @Transactional
    public PetDTO feed(Long id) {
        Pet p = repo.findById(id).orElseThrow(() -> new PetNotFoundException(id));
        p.setHungerLevel(Math.max(0, p.getHungerLevel() - 10));
        return toDTO(repo.save(p));
    }

    @Transactional
    public PetDTO play(Long id) {
        Pet p = repo.findById(id).orElseThrow(() -> new PetNotFoundException(id));
        p.setHappiness(Math.min(100, p.getHappiness() + 10));
        return toDTO(repo.save(p));
    }

    @Transactional
    public void release(Long id) {
        if (!repo.existsById(id)) throw new PetNotFoundException(id);
        repo.deleteById(id);
    }
}