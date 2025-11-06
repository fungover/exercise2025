package org.example.controller;

import jakarta.validation.Valid;
import org.example.entity.Animal;
import org.example.service.AnimalService;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public List<Animal> findAll() {
        return animalService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> get(@PathVariable Long id) {
        return animalService.findById(id)
                            .map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound()
                                                  .build());
    }

    @PostMapping
    public ResponseEntity<Animal> create(@RequestBody @Valid Animal animal) {
        Animal saved = animalService.save(animal);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> update(@PathVariable Long id,
                                         @RequestBody Animal animal) {
        return animalService.findById(id)
                            .map(existing -> {
                                animal.setId(id);
                                return ResponseEntity.ok(animalService.save(animal));
                            })
                            .orElse(ResponseEntity.notFound()
                                                  .build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        animalService.delete(id);
    }
}
