package org.example.pets.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.pets.dto.PetDTO;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@ApplicationScoped
public class PetService {

    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();

    private final AtomicLong idSequence = new AtomicLong(1);

    private final ConcurrentHashMap<Long, ReentrantLock> locks = new ConcurrentHashMap<>();

    //Todo Adopt logic
    public PetDTO adopt(PetDTO pet) {
        long id = idSequence.getAndIncrement();
        pet.setId(id);
        pets.put(id, takeCopy(pet));
        locks.put(id, new ReentrantLock());
        return takeCopy(pet);
    }

    public List<PetDTO> list() {
        return pets.values().stream()
            .map(this::takeCopy)
                .collect(Collectors.toList());
    }

        public Optional<PetDTO> find(long id) {
            PetDTO p = pets.get(id);
            return p == null ? Optional.empty() : Optional.of(takeCopy(p));
        }

        //Todo Feed logic
        public PetDTO feed(long id, int amount) {
            ReentrantLock lock = locks.get(id);
            if (lock == null) throw new IllegalArgumentException("Pet not found");
            lock.lock();
            try {
                PetDTO existing = pets.get(id);
                if (existing == null) throw new IllegalArgumentException("Pet not found");
                int newHungerLevel = Math.max(0,existing.getHungerLevel() - amount);
                existing.setHungerLevel(newHungerLevel);
                return takeCopy(existing);
            } finally {
                lock.unlock();
            }
    }

    //Todo Play logic
    public PetDTO play(long id, int amount){
        ReentrantLock lock = locks.get(id);
        if (lock == null) throw new IllegalArgumentException("Pet not found");

        lock.lock();

        try {
            PetDTO pet = pets.get(id);
            if (pet == null) throw new IllegalArgumentException("Pet not found");
            //pet.setHappinessLevel(pet.getHappinessLevel() + amount);

            int newHappinessLevel = Math.min(100, pet.getHappinessLevel() + amount);
            pet.setHappinessLevel(newHappinessLevel);

            return takeCopy(pet);
        } finally {
            lock.unlock();
        }
    }

    //Todo Release logic
    public void release(long id){
        PetDTO removed = pets.remove(id);
        if (removed == null) throw new IllegalArgumentException("Pet not found");
        locks.remove(id);
    }
    //Todo Copy logic
    private PetDTO takeCopy(PetDTO pet){
        PetDTO copy = new PetDTO();
        copy.setId(pet.getId());
        copy.setName(pet.getName());
        copy.setSpecies(pet.getSpecies());
        copy.setHungerLevel(pet.getHungerLevel());
        copy.setHappinessLevel(pet.getHappinessLevel());
        return copy;
    }


}
