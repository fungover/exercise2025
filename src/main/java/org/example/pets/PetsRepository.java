package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;

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

    return petsMap.computeIfPresent(longId, (key, pet) -> new Pets(
            pet.id(),
            pet.name(),
            String.valueOf(Long.parseLong(pet.hungerLevel()) - longAmount),
            pet.happiness()
            )
    );
  }

  @Override
  public Pets play(String id, String amount) {
    Long longId = Long.parseLong(id);
    long longAmount = Long.parseLong(amount);

    return petsMap.computeIfPresent(longId, (key, pet) -> new Pets(
            pet.id(),
            pet.name(),
            pet.hungerLevel(),
            String.valueOf(Long.parseLong(pet.happiness()) + longAmount)
            )
    );
  }
}
