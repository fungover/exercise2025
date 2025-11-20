package org.example;

import org.example.enteties.Pet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class PetController {

    private final PetRepository repository;

    public PetController(PetRepository repository) {
        this.repository = repository;
    }
    @GetMapping("pets")
    @PreAuthorize("hasRole('API')")
    public List<PetDTO> getAllPets(){
        return repository.findAll().stream()
                .map(pet -> new PetDTO(pet.getName(), pet.getSpecies(), pet.getHunger(), pet.getHappiness(), pet.getId()))
                .toList();
    }

    @GetMapping("pets/{id}")
    public ResponseEntity<PetDTO> getPet(@PathVariable Integer id){
        PetDTO petWithId = (repository.findPetById(id)
                .map(pet -> new PetDTO(pet.getName(), pet.getSpecies(), pet.getHunger(), pet.getHappiness(), pet.getId()))
                .orElseThrow());
        return new ResponseEntity<>(petWithId, HttpStatus.OK);
    }

     @PostMapping("pets")
     @PreAuthorize("hasRole('API')")
    public ResponseEntity<Void> adoptPet(@RequestBody Pet newPet){
         return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("pets/{id}")
    @PreAuthorize("hasRole('API')")
    public ResponseEntity<Void> deletePet(@PathVariable Integer id){
        repository.deletePetById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("pets/{id}/feed")
    public ResponseEntity<PetDTO> feedPet(@PathVariable Integer id){
        PetDTO petDto = PetService.feedPet(repository.findPetById(id)
                .map(pet -> new PetDTO(pet.getName(), pet.getSpecies(), pet.getHunger(), pet.getHappiness(), pet.getId()))
                .orElseThrow());
        Pet fedPet = new Pet(petDto.getName(), petDto.getSpecies(), petDto.getHunger(), petDto.getHappiness(), petDto.getId());
        repository.save(fedPet);
        return new ResponseEntity<>(petDto, HttpStatus.OK);
    }
    @PutMapping("pets/{id}/play")
    public ResponseEntity<PetDTO> playWithPet(@PathVariable Integer id){
        System.out.println("In controller.playWithPet()");
        PetDTO petToPlayWith = repository.findPetById(id)
                .map(pet -> new PetDTO(pet.getName(), pet.getSpecies(), pet.getHunger(), pet.getHappiness(), pet.getId()))
                .orElseThrow();
        System.out.println("petToPlayWith:");
        System.out.println(petToPlayWith.getName());
        PetDTO petDto = PetService.playWithPet(petToPlayWith);
        System.out.println(petDto);
        Pet playedWithPet = new Pet(petDto.getName(), petDto.getSpecies(), petDto.getHunger(), petDto.getHappiness(), petDto.getId());
        repository.save(playedWithPet);
        return new ResponseEntity<>(petDto, HttpStatus.OK);
    }
}
