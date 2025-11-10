package org.example.api.director;

import jakarta.validation.Valid;
import org.example.dto.request.director.CreateDirectorRequest;
import org.example.dto.request.director.UpdateDirectorRequest;
import org.example.dto.response.director.DirectorResponse;
import org.example.entities.Director;
import org.example.mapper.director.DirectorMapper;
import org.example.service.director.DirectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/director")
public class DirectorController {
    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @PostMapping
    public ResponseEntity<DirectorResponse> addDirector(@Valid @RequestBody CreateDirectorRequest request) {

        Director director = DirectorMapper.toEntity(request);
        Director savedDirector = directorService.addDirector(director);
        DirectorResponse response = DirectorMapper.toResponse(savedDirector);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<DirectorResponse>> getAllDirectors() {

        List<DirectorResponse> responses = directorService.getAllDirectors().stream()
                .map(DirectorMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorResponse> getDirector(@PathVariable Long id) {

        Director director = directorService.getDirector(id);
        DirectorResponse response = DirectorMapper.toResponse(director);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectorResponse> updateDirector(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDirectorRequest request) {

        Director existingDirector = directorService.getDirector(id);

        Director director = DirectorMapper.toEntity(existingDirector, request);
        Director updatedDirector = directorService.updateDirector(id, director);

        DirectorResponse response = DirectorMapper.toResponse(updatedDirector);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable Long id) {

        directorService.deleteDirector(id);

        return ResponseEntity.noContent().build();
    }
}
