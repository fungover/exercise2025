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

  public Response addPet(Pets pet) {
    petsRepository.add(pet);
    return Response.status(Response.Status.CREATED).entity(pet).build();
  }

  public List<Pets> getPets() {
    return petsRepository.getAll();
  }
}
