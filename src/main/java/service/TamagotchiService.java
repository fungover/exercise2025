package service;

import dto.TamagotchiDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

// Service class managing Tamagotchi business logic
// ApplicationScoped ensures only one instance exists throughout the application lifecycle
@ApplicationScoped
public class TamagotchiService {

    // Thread-safe in-memory storage for Tamagotchis
    // ConcurrentHashMap allows safe concurrent access from multiple threads
    private final ConcurrentHashMap<Long, TamagotchiDTO> tamagotchis = new ConcurrentHashMap<>();

    // Atomic counter for generating unique IDs
    // AtomicLong ensures thread-safe ID generation
    private final AtomicLong idCounter = new AtomicLong(1);

    // Hatch a new Tamagotchi and initialize with default values
    public TamagotchiDTO hatchTamagotchi(TamagotchiDTO tamagotchi) {
        // Generate next unique ID
        Long id = idCounter.getAndIncrement();
        tamagotchi.setId(id);

        // Initialize starting values for new Tamagotchi
        tamagotchi.setAge(0);
        tamagotchi.setHunger(50);
        tamagotchi.setHappiness(50);
        tamagotchi.setHealth(100);
        tamagotchi.setEnergy(100);
        tamagotchi.setSleeping(false);
        tamagotchi.setNeedsCleaning(false);

        // Update status based on initial values
        updateStatus(tamagotchi);

        // Store in memory
        tamagotchis.put(id, tamagotchi);
        return tamagotchi;
    }

    // Retrieve all Tamagotchis
    public List<TamagotchiDTO> getAllTamagotchis() {
        return new ArrayList<>(tamagotchis.values());
    }

    // Retrieve a specific Tamagotchi by ID
    // Returns Optional to handle cases where Tamagotchi doesn't exist
    public Optional<TamagotchiDTO> getTamagotchiById(Long id) {
        return Optional.ofNullable(tamagotchis.get(id));
    }

    // Feed the Tamagotchi to reduce hunger
    public TamagotchiDTO feedTamagotchi(Long id) {
        TamagotchiDTO tama = tamagotchis.get(id);
        if (tama == null) return null;

        synchronized (tama) {
            // Cannot feed a sleeping Tamagotchi
            if (tama.isSleeping()) {
                throw new IllegalStateException("Kan inte mata en sovande Tamagotchi!");
            }

            // Reduce hunger by 25 (minimum 0)
            tama.setHunger(Math.max(0, tama.getHunger() - 25));

            // Increase health by 5 (maximum 100)
            tama.setHealth(Math.min(100, tama.getHealth() + 5));

            // Decrease energy slightly by 5
            tama.setEnergy(Math.max(0, tama.getEnergy() - 5));

            // 30% chance that feeding creates a mess
            if (Math.random() > 0.7) {
                tama.setNeedsCleaning(true);
            }

            updateStatus(tama);
        }
        return tama;
    }

    // Play with the Tamagotchi to increase happiness
    public TamagotchiDTO playWithTamagotchi(Long id) {
        TamagotchiDTO tama = tamagotchis.get(id);
        if (tama == null) return null;

        // Cannot play with a sleeping Tamagotchi
        if (tama.isSleeping()) {
            throw new IllegalStateException("Kan inte leka med en sovande Tamagotchi!");
        }

        // Cannot play if too tired (energy below 20)
        if (tama.getEnergy() < 20) {
            throw new IllegalStateException("Tamagotchi är för trött för att leka!");
        }

        // Increase happiness by 20 (maximum 100)
        tama.setHappiness(Math.min(100, tama.getHappiness() + 20));

        // Decrease energy by 15 (playing is tiring)
        tama.setEnergy(Math.max(0, tama.getEnergy() - 15));

        // Increase hunger by 10 (playing makes hungry)
        tama.setHunger(Math.min(100, tama.getHunger() + 10));

        updateStatus(tama);
        return tama;
    }

    // Put Tamagotchi to sleep
    public TamagotchiDTO putToSleep(Long id) {
        TamagotchiDTO tama = tamagotchis.get(id);
        if (tama == null) return null;

        // Check if already sleeping
        if (tama.isSleeping()) {
            throw new IllegalStateException("Tamagotchi sover redan!");
        }

        tama.setSleeping(true);
        updateStatus(tama);
        return tama;
    }

    // Wake up the Tamagotchi
    public TamagotchiDTO wakeUp(Long id) {
        TamagotchiDTO tama = tamagotchis.get(id);
        if (tama == null) return null;

        // Check if actually sleeping
        if (!tama.isSleeping()) {
            throw new IllegalStateException("Tamagotchi är redan vaken!");
        }

        // Restore energy after sleep
        tama.setSleeping(false);
        tama.setEnergy(100);

        // Increase hunger after sleep
        tama.setHunger(Math.min(100, tama.getHunger() + 20));

        updateStatus(tama);
        return tama;
    }

    // Clean up after the Tamagotchi
    public TamagotchiDTO cleanTamagotchi(Long id) {
        TamagotchiDTO tama = tamagotchis.get(id);
        if (tama == null) return null;

        // Check if cleaning is needed
        if (!tama.isNeedsCleaning()) {
            throw new IllegalStateException("Tamagotchi är redan ren!");
        }

        // Clean and improve health and happiness
        tama.setNeedsCleaning(false);
        tama.setHealth(Math.min(100, tama.getHealth() + 10));
        tama.setHappiness(Math.min(100, tama.getHappiness() + 5));

        updateStatus(tama);
        return tama;
    }

    // Give medicine to sick Tamagotchi
    public TamagotchiDTO giveMedicine(Long id) {
        TamagotchiDTO tama = tamagotchis.get(id);
        if (tama == null) return null;

        // Only give medicine if health is below 50
        if (tama.getHealth() > 50) {
            throw new IllegalStateException("Tamagotchi är inte sjuk!");
        }

        // Increase health by 30
        tama.setHealth(Math.min(100, tama.getHealth() + 30));

        // Decrease happiness (Tamagotchi doesn't like medicine)
        tama.setHappiness(Math.max(0, tama.getHappiness() - 10));

        updateStatus(tama);
        return tama;
    }

    // Release (delete) a Tamagotchi
    public boolean releaseTamagotchi(Long id) {
        return tamagotchis.remove(id) != null;
    }

    // Update status based on current attribute values
    // Priority: sleeping > sick > starving > exhausted > very happy > sad > needs cleaning > healthy
    private void updateStatus(TamagotchiDTO tama) {
        if (tama.isSleeping()) {
            tama.setStatus("Sleeping");
        } else if (tama.getHealth() < 30) {
            tama.setStatus("Sick");
        } else if (tama.getHunger() > 80) {
            tama.setStatus("Starving");
        } else if (tama.getEnergy() < 20) {
            tama.setStatus("Exhausted");
        } else if (tama.getHappiness() > 80) {
            tama.setStatus("Very Happy");
        } else if (tama.getHappiness() < 30) {
            tama.setStatus("Sad");
        } else if (tama.isNeedsCleaning()) {
            tama.setStatus("Needs Cleaning");
        } else {
            tama.setStatus("Healthy");
        }
    }

    // Filter Tamagotchis by character type
    public List<TamagotchiDTO> getTamagotchisByCharacter(String character) {
        if (character == null || character.isEmpty()) {
            return new ArrayList<>();
        }
        return tamagotchis.values().stream()
                .filter(tama -> tama.getCharacter().equalsIgnoreCase(character))
                .collect(Collectors.toList());
    }

    // Sort Tamagotchis by specified attribute
    public List<TamagotchiDTO> getSortedTamagotchis(String sortBy, String order) {
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "id";
        }
        List<TamagotchiDTO> tamaList = new ArrayList<>(tamagotchis.values());

        // Determine comparator based on sortBy parameter
        Comparator<TamagotchiDTO> comparator;
        switch (sortBy.toLowerCase()) {
            case "happiness":
                comparator = Comparator.comparing(TamagotchiDTO::getHappiness);
                break;
            case "health":
                comparator = Comparator.comparing(TamagotchiDTO::getHealth);
                break;
            case "age":
                comparator = Comparator.comparing(TamagotchiDTO::getAge);
                break;
            case "energy":
                comparator = Comparator.comparing(TamagotchiDTO::getEnergy);
                break;
            default:
                comparator = Comparator.comparing(TamagotchiDTO::getId);
        }

        // Reverse order if descending
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        tamaList.sort(comparator);
        return tamaList;
    }

    // BONUS: Paginate results for large collections
    public List<TamagotchiDTO> getPaginatedTamagotchis(int offset, int limit) {
        if (offset < 0 ) {
            throw new IllegalArgumentException("Offset cannot be negative!");
        }
        if (limit < 0) {
            throw new IllegalArgumentException("Limit cannot be negative!");
        }
        return tamagotchis.values().stream()
                .sorted(Comparator.comparing(TamagotchiDTO::getId))
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }
}