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
  public Pets add(Pets pet) {
    Long id = idGenerator.getAndIncrement();
    Pets newPet = new Pets(id, pet.name(), pet.hungerLevel(), pet.happiness());
    pets.add(newPet);
    return newPet;
  }

  @Override
  public List<Pets> getAll() {
    return pets;
  }

  @Override
  public Pets getById(String id) {
    var longId = Long.valueOf(id);
    for(Pets pet : pets){
      if(pet.id().equals(longId)){
        return pet;
      }
    }
    return null;
  }

  @Override
  public Pets remove(String id) {
    Long longId = Long.valueOf(id);
    for (int i = 0; i < pets.size(); i++) {
      Pets pet = pets.get(i);
      if (pet.id().equals(longId)) {
        pets.remove(i);
        return pet;
      }
    }
    return null;
  }

  @Override
  public Pets feed(String id, String amount) {
    var longId = Long.valueOf(id);
    var hungerLevel = Long.parseLong(amount);
    for(int i = 0; i < pets.size(); i++){
      Pets pet = pets.get(i);
      if(pet.id().equals(longId)){
        Pets updated = new Pets(pet.id(),
                pet.name(),
                String.valueOf(Long.parseLong(pet.hungerLevel()) - hungerLevel),
                pet.happiness());
        pets.set(i, updated);
        return updated;
      }
    }
    return null;
  }

  @Override
  public Pets play(String id, String amount) {
    var longId = Long.valueOf(id);
    var happinessLevel = Long.parseLong(amount);
    for(int i = 0; i < pets.size(); i++){
      Pets pet = pets.get(i);
      if(pet.id().equals(longId)){
        Pets updated = new Pets(pet.id(),
                pet.name(),
                pet.hungerLevel(),
                String.valueOf(Long.parseLong(pet.happiness()) + happinessLevel)
        );
        pets.set(i, updated);
        return updated;
      }
    }
    return null;
  }
}
