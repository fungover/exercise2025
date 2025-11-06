package org.example.controllers;

import jakarta.validation.Valid;
import org.example.DTO.CatchYearDTO;
import org.example.services.CatchService;
import org.example.DTO.CreateCatchDTO;
import org.example.DTO.ErrorResponseDTO;
import org.example.entities.Catch;
import org.example.services.CatchService;
import org.example.repository.CatchRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/catches")
public class CatchController {

    private final CatchRepository catchRepository;
    private final CatchService catchService;

    public CatchController(CatchRepository catchRepository, CatchService catchService) {
        this.catchRepository = catchRepository;
        this.catchService = catchService;
    }

    /**
     * Hämtar alla fångster
     */
    @GetMapping
    public ResponseEntity<List<Catch>> getAllCatches() {
        List<Catch> catches = catchService.getAllCatches();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(catchService.countCatches()))
                .body(catches);
    }

    /**
     * Hämtar en specifik fångst via ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Catch> getCatchById(@PathVariable Long id) {
        return catchService.getCatchById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Hämtar fångster sorterade efter vikt
     */
    @GetMapping("/weight")
    public ResponseEntity<?> getCatchesOrderedByWeight(
            @RequestParam(defaultValue = "asc") String order) {

        if (!order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc")) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponseDTO(
                            400,
                            "Bad Request",
                            "Invalid order parameter. Must be either 'asc' or 'desc'."
                    )
            );
        }
        
        boolean stmt = order.equalsIgnoreCase("asc");
        List<CatchYearDTO> result = catchService.getCatchesOrderedByWeight(stmt);

        return ResponseEntity.ok(result);
    }

    /**
     * Skapar en ny fångst
     */
    @PostMapping
    // Disable XSS warning (Sonar/IntelliJ)
    public ResponseEntity<Catch> createCatch(@Valid @RequestBody CreateCatchDTO catchDTO) {
        Catch created = catchService.createCatch(catchDTO);
        return ResponseEntity
                .created(URI.create("/api/catches/" + created.getId()))
                .body(created);
    }

    /**
     * Raderar en fångst via ID
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCatch(@PathVariable Long id) {
        if (!catchRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        catchRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}