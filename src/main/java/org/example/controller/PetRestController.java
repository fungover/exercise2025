package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.PetDTO;
import org.example.entity.Pet;
import org.example.service.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetRestController {

    private final PetService service;

    @PostMapping
    public ResponseEntity<PetDTO> adopt(@Valid @RequestBody Pet pet) {
        return ResponseEntity.ok(service.adopt(pet));
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("/{id}/feed")
    public ResponseEntity<PetDTO> feed(@PathVariable Long id) {
        return ResponseEntity.ok(service.feed(id));
    }

    @PutMapping("/{id}/play")
    public ResponseEntity<PetDTO> play(@PathVariable Long id) {
        return ResponseEntity.ok(service.play(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> release(@PathVariable Long id) {
        service.release(id);
        return ResponseEntity.noContent().build();
    }
}
