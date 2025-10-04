package org.example.pets;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Dependent
public class PetsService {
  private final PetsRepository petsRepository;

  @Inject
  public PetsService(PetsRepository petsRepository) {
    this.petsRepository = petsRepository;
  }

  public void addPet(Pets pet) {
    petsRepository.add(pet);
    Response.status(Response.Status.CREATED).entity(pet).build();
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

  public void feedPet(String id, String amount) {
    petsRepository.feed(id, amount);
  }

  public void playPet(String id, String amount) {
    petsRepository.play(id, amount);
  }
}
