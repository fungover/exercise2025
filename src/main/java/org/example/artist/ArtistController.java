package org.example.artist;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistRepository repository;

    public ArtistController(ArtistRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Artist> getAll() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artist create(@RequestBody Artist artist) {
        return repository.save(artist);
    }
}

