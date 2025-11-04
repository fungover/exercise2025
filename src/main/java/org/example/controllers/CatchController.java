package org.example.controllers;

import jakarta.validation.Valid;
import org.example.DTO.CatchYearDTO;
import org.example.entities.Catch;
import org.example.repository.CatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController("api")
public class CatchController {

    private final CatchRepository catchRepository;
    /*private final SanitizationService sanitizationService;*/


    public CatchController(CatchRepository catchRepository/*, SanitizationService sanitizationService*/) {
        this.catchRepository = catchRepository;
        /*this.sanitizationService = sanitizationService;*/
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
                .body(saved) ;
    }

    // Delete catch by id

}
