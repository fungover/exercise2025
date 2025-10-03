package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class PetsRepository implements Repository {
  List<Pets> pets = new CopyOnWriteArrayList<Pets>();

  @Override
  public void add(Pets pet) {
    pets.add(pet);
  }

  @Override
  public List<Pets> getAll() {
    return pets;
  }

  @Override
  public Pets getById(String id) {
    for(Pets pet : pets){
      if(pet.id().equals(id)){
        return pet;
      }
    }
    return null;
  }
}
