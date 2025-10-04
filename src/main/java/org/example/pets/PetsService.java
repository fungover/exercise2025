package org.example.pets;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.List;

@Dependent
public class PetsService {
  private final PetsRepository petsRepository;

  @Inject
  public PetsService(PetsRepository petsRepository) {
    this.petsRepository = petsRepository;
  }

  public Pets addPet(Pets pet) {
    return petsRepository.add(pet);
  }

  public List<Pets> getPets() {
    return petsRepository.getAll();
  }

  public Pets getById(String id){
    return petsRepository.getById(id);
  }

  public Pets removePet(String id) {
    return petsRepository.remove(id);
  }

  public Pets feedPet(String id, String amount) {
    return petsRepository.feed(id, amount);
  }

  public Pets playPet(String id, String amount) {
    return petsRepository.play(id, amount);
  }
}
