package exercise8.controller.api;

import exercise8.entity.Patrol;
import exercise8.service.PatrolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patrols")
public class PatrolRestController {

    @Autowired
    private PatrolService patrolService;

    // GET /api/patrols - Get all scout troops
    @GetMapping
    public ResponseEntity<List<Patrol>> getAllPatrols() {
        List<Patrol> patrols = patrolService.findAll();
        return ResponseEntity.ok(patrols);
    }

    // GET /api/patrols/{id} - Get specific scout corps
    @GetMapping("/{id}")
    public ResponseEntity<Patrol> getPatrolById(@PathVariable Long id) {
        Patrol patrol = patrolService.findById(id);
        return ResponseEntity.ok(patrol);
    }

    // POST /api/patrols - Create a new scout troop
    @PostMapping
    public ResponseEntity<Patrol> createPatrol(@Valid @RequestBody Patrol patrol) {
        Patrol createdPatrol = patrolService.create(patrol);
        return new ResponseEntity<>(createdPatrol, HttpStatus.CREATED);
    }

    // PUT /api/patrols/{id} - Update scout corps
    @PutMapping("/{id}")
    public ResponseEntity<Patrol> updatePatrol(
            @PathVariable Long id,
            @Valid @RequestBody Patrol patrol) {
        Patrol updatedPatrol = patrolService.update(id, patrol);
        return ResponseEntity.ok(updatedPatrol);
    }

    // DELETE /api/patrols/{id} - Delete Scout Corps
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatrol(@PathVariable Long id) {
        patrolService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/patrols/district/{district} - Scout corps in a district
    @GetMapping("/district/{district}")
    public ResponseEntity<List<Patrol>> getPatrolsByDistrict(@PathVariable String district) {
        List<Patrol> patrols = patrolService.findByDistrict(district);
        return ResponseEntity.ok(patrols);
    }
}
