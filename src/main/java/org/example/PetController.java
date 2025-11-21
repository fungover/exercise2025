package org.example;

import jakarta.persistence.EntityNotFoundException;
import org.example.entities.Pet;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("api")
public class PetController {
    private final PetRepository repository;

    public PetController(PetRepository repository) {
        this.repository = repository;
    }

    @GetMapping("pets")
    public List<Pet> getPets() {
        return repository.findAll().stream()
                .map(p -> new Pet(p.getName(), p.getAge(), p.getSpecies(), p.getHungerLevel(), p.getHappiness()))
                .toList();
    }

    @PostMapping("addPet")
    public String addPet(@RequestBody Pet pet) {
        PetService service = new PetService(repository);

        service.addPet(pet);

        return "You added a " + pet.getSpecies() + " named " + pet.getName();
    }

    @GetMapping("pets/{id}")
    public Pet getPet(@PathVariable Integer id) {
        PetService service = new PetService(repository);

        return service.getPet(id);
    }

    @DeleteMapping("/deletePet/{id}")
    public ResponseEntity<String> deletePet(@PathVariable Integer id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok("Deleted pet with id: " + id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No pet with id: " + id);
        }
    }

    @PostMapping("/feed/{id}")
    public ResponseEntity<String> feedPet(@PathVariable Integer id) {
        PetService service = new PetService(repository);
        try {
            Pet fedPet = service.feedPet(id);
            repository.save(fedPet);
            return ResponseEntity.ok("You just fed " + fedPet.getName() + " its hunger is now at " + fedPet.getHungerLevel() + "%");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/play/{id}")
    public ResponseEntity<String> playPet(@PathVariable Integer id) {
        PetService service = new PetService(repository);
        try {
            Pet happyPet = service.playPet(id);
            repository.save(happyPet);
            return ResponseEntity.ok("You just played with " + happyPet.getName() + " its happiness is now at " + happyPet.getHappiness() + "%");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

