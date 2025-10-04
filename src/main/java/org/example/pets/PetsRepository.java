package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@ApplicationScoped
public class PetsRepository implements Repository {
  private final ConcurrentHashMap<Long, Pets> petsMap = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  @Override
  public Pets add(Pets pet) {
    Long id = idGenerator.getAndIncrement();
    Pets newPet = new Pets(id, pet.name(), pet.hungerLevel(), pet.happiness());
    petsMap.put(id, newPet);
    return newPet;
  }

  @Override
  public List<Pets> getAll() {
    return petsMap.values().stream().collect(Collectors.toList());
  }

  @Override
  public Pets getById(String id) {
    Long longId = Long.parseLong(id);
    return petsMap.get(longId);
  }

  @Override
  public Pets remove(String id) {
    Long longId = Long.parseLong(id);
    return petsMap.remove(longId);
  }

  @Override
  public Pets feed(String id, String amount) {
    Long longId = Long.parseLong(id);
    long longAmount = Long.parseLong(amount);

    return petsMap.computeIfPresent(longId, (key, pet) -> {
      long newHunger = Long.parseLong(pet.hungerLevel()) - longAmount;
      if(newHunger < 0){
        throw new IllegalArgumentException("Hunger level cannot be less than 0");
      }
      return new Pets(
            pet.id(),
            pet.name(),
            String.valueOf(newHunger),
            pet.happiness());
    });
  }

  @Override
  public Pets play(String id, String amount) {
    Long longId = Long.valueOf(id);
    long longAmount = Long.parseLong(amount);

    return petsMap.computeIfPresent(longId, (k, pet) -> {
      long newHappiness = Long.parseLong(pet.happiness()) + longAmount;
      if (newHappiness > 20) {
        throw new IllegalArgumentException("Happiness level cannot be greater than 20");
      }
      return new Pets(
              pet.id(),
              pet.name(),
              pet.hungerLevel(),
              String.valueOf(newHappiness));
    });
  }

  @Override
  public List<Pets> findByName(String name) {
    if (name == null || name.isBlank()) {
      return new ArrayList<>(petsMap.values());
    }
    return petsMap.values().stream()
            .filter(p -> p.name().equalsIgnoreCase(name))
            .toList();
  }
}
