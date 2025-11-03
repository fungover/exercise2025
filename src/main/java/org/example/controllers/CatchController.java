package org.example.controllers;

import org.example.entities.Catch;
import org.example.repository.CatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController("api")
public class CatchController {

    private final CatchRepository catchRepository;


    public CatchController(CatchRepository catchRepository) {
        this.catchRepository = catchRepository;
    }


    // Gets all catches
    @GetMapping("catches")
    public ResponseEntity getAllCatches() {
        return ResponseEntity.status(200).header("Catches total", String.valueOf(catchRepository.count())).body(catchRepository.findAll());
    }



    // Add catch (Post)
    @PostMapping("catches")
    public ResponseEntity<Catch> createCatch(@RequestBody Catch c) {
        Catch saved = catchRepository.save(c);
        return ResponseEntity.status(201).build() ;
    }

    // Delete catch by id

}
