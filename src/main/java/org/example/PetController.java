package org.example;

import jakarta.persistence.EntityNotFoundException;
import org.example.entities.Pet;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class PetController {
    private final PetRepository repository;
    private final PetService service;

    public PetController(PetRepository repository, PetService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("pets")
    public List<Pet> getPets() {
        return repository.findAll().stream().toList();
    }

    @PostMapping("addPet")
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
        Pet saved = service.addPet(pet);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("pets/{id}")
    public ResponseEntity<?> getPet(@PathVariable Integer id) {
        Pet pet = service.getPet(id);
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pet);
    }

    @DeleteMapping("/deletePet/{id}")
    public ResponseEntity<String> deletePet(@PathVariable Integer id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No pet with id: " + id);
        }

        service.removePet(id);
        return ResponseEntity.ok("Deleted pet with id: " + id);
    }

    @PostMapping("/feed/{id}")
    public ResponseEntity<String> feedPet(@PathVariable Integer id) {
        Pet fedPet = service.feedPet(id);

        if (fedPet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found with id: " + id);
        }

        return ResponseEntity.ok("You just fed " + fedPet.getName() + " its hunger is now at " + fedPet.getHungerLevel() + "%");
    }

    @PostMapping("/play/{id}")
    public ResponseEntity<String> playPet(@PathVariable Integer id) {
        Pet happyPet = service.playPet(id);

        if (happyPet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found with id: " + id);
        }

        return ResponseEntity.ok("You just played with " + happyPet.getName() + " its happiness is now at " + happyPet.getHappiness() + "%");
    }
}

