package org.example.controllers;

import jakarta.validation.Valid;
import org.example.DTO.CatchYearDTO;
import org.example.entities.Catch;
import org.example.repository.CatchRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class CatchController {

    private final CatchRepository catchRepository;

    public CatchController(CatchRepository catchRepository) {
        this.catchRepository = catchRepository;
    }


    // Gets all catches
    @GetMapping("catches")
    public ResponseEntity<Object> getAllCatches() {
        return ResponseEntity.status(200).header("Catches total", String.valueOf(catchRepository.count())).body(catchRepository.findAll());
    }

    // Add catch (Post)
    @PostMapping("catches")
    public ResponseEntity<Catch> createCatch(@Valid @RequestBody Catch c) {
        /*c.setSpecies(sanitizationService.sanitize(c.getSpecies()));*/
        Catch saved = catchRepository.save(c);
        return ResponseEntity
                .created(URI.create("/api/catches/" + saved.getId()))
                .body(saved);
    }

    // Delete catch by id
    @DeleteMapping("/catches/{id}")
    public ResponseEntity<Void> deleteCatch(@PathVariable Long id) {
        if (!catchRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        catchRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("catches/{id}")
    public ResponseEntity<Catch> getCatchById(@PathVariable Long id) {
        return catchRepository.findById(id).stream()
                .findFirst()
                .map(c -> ResponseEntity.ok().body(c))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("catches/weight")
    public ResponseEntity<?> getCatchesOrderedByWeight(@RequestParam(defaultValue = "asc") String order) {

        List<CatchYearDTO> result;

        if (order.equals("asc")) {
            result = catchRepository.orderByWeightAsc();
        } else if (order.equals("desc")) {
            result = catchRepository.orderByWeightDesc();
        } else {
            return ResponseEntity.status(400).body(
                    new org.example.backend.DTO.ErrorResponseDTO(400, "Bad Request", "Invalid order parameter. Must be either 'asc' or 'desc'.")
            );
        }
        return ResponseEntity.ok(result);
    }

}
