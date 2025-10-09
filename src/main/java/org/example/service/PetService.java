package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.example.api.PetMapper;
import org.example.domain.Pet;
import org.example.dto.PetDTO;
import org.example.repo.PetRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PetService {

    PetRepository repo;

    public PetService() {}

    // CDI injects the repo in the app
    @Inject
    public PetService(PetRepository repo) {
        this.repo = repo;
    }

    public PetDTO adopt(PetDTO incoming) {
        Pet saved = repo.add(PetMapper.toDomainNew(incoming));
        return PetMapper.toDTO(saved);
    }

    public List<PetDTO> list() {
        return repo.findAll().stream().map(PetMapper::toDTO).collect(Collectors.toList());
    }

    public PetDTO get(long id) {
        Pet p = repo.findById(id).orElseThrow(() -> new NotFoundException("Pet " + id + " not found"));
        return PetMapper.toDTO(p);
    }

    public PetDTO feed(long id, int amount) {
        Pet updated = repo.update(id, p -> p.feed(amount));
        return PetMapper.toDTO(updated);
    }

    public PetDTO play(long id, int amount) {
        Pet updated = repo.update(id, p -> p.play(amount));
        return PetMapper.toDTO(updated);
    }

    public void release(long id) {
        if (!repo.delete(id)) throw new NotFoundException("Pet " + id + " not found");
    }
}
