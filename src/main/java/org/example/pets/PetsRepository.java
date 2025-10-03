package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class PetsRepository implements Repository {
  List<Pets> pets = new CopyOnWriteArrayList<Pets>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  @Override
  public void add(Pets pet) {
    Long id = idGenerator.getAndIncrement();
    Pets newPet = new Pets(id, pet.name(), pet.hungerLevel(), pet.happiness());
    pets.add(newPet);
  }

  @Override
  public List<Pets> getAll() {
    return pets;
  }

  @Override
  public Pets getById(Long id) {
    for(Pets pet : pets){
      if(pet.id().equals(id)){
        return pet;
      }
    }
    return null;
  }

  @Override
  public Pets remove(Long id) {
    for(Pets pet : pets){
      if(pet.id().equals(id)){
        pets.remove(pet);
      }
    }
    return null;
  }

  @Override
  public Pets update(Long id) {
    return null;
  }


}
