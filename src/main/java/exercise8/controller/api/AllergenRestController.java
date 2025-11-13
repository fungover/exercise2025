package exercise8.controller.api;

import exercise8.entity.Allergen;
import exercise8.service.AllergenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/allergens")
public class AllergenRestController {

    @Autowired
    private AllergenService allergenService;

    // GET /api/allergens - Hämta alla allergener
    @GetMapping
    public ResponseEntity<List<Allergen>> getAllAllergens() {
        List<Allergen> allergens = allergenService.findAll();
        return ResponseEntity.ok(allergens);
    }

    // GET /api/allergens/{id} - Hämta specifik allergen
    @GetMapping("/{id}")
    public ResponseEntity<Allergen> getAllergenById(@PathVariable Long id) {
        Allergen allergen = allergenService.findById(id);
        return ResponseEntity.ok(allergen);
    }

    // POST /api/allergens - Skapa ny allergen
    @PostMapping
    public ResponseEntity<Allergen> createAllergen(@Valid @RequestBody Allergen allergen) {
        Allergen createdAllergen = allergenService.create(allergen);
        return new ResponseEntity<>(createdAllergen, HttpStatus.CREATED);
    }

    // PUT /api/allergens/{id} - Uppdatera allergen
    @PutMapping("/{id}")
    public ResponseEntity<Allergen> updateAllergen(
            @PathVariable Long id,
            @Valid @RequestBody Allergen allergen) {
        Allergen updatedAllergen = allergenService.update(id, allergen);
        return ResponseEntity.ok(updatedAllergen);
    }

    // DELETE /api/allergens/{id} - Ta bort allergen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAllergen(@PathVariable Long id) {
        allergenService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/allergens/critical - Hämta kritiska allergener
    @GetMapping("/critical")
    public ResponseEntity<List<Allergen>> getCriticalAllergens() {
        List<Allergen> allergens = allergenService.findCriticalAllergens();
        return ResponseEntity.ok(allergens);
    }
}