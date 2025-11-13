package exercise8.service;

import exercise8.entity.Allergen;
import exercise8.entity.AllergenSeverity;
import exercise8.exception.ResourceNotFoundException;
import exercise8.repository.AllergenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AllergenService {

    @Autowired
    private AllergenRepository allergenRepository;

    // Hämta alla allergener
    public List<Allergen> findAll() {
        return allergenRepository.findAll();
    }

    // Hämta allergen via ID
    public Allergen findById(Long id) {
        return allergenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allergen", "id", id));
    }

    // Skapa ny allergen
    public Allergen create(Allergen allergen) {
        return allergenRepository.save(allergen);
    }

    // Uppdatera allergen
    public Allergen update(Long id, Allergen allergenDetails) {
        Allergen allergen = findById(id);

        allergen.setName(allergenDetails.getName());
        allergen.setDescription(allergenDetails.getDescription());
        allergen.setSeverity(allergenDetails.getSeverity());

        return allergenRepository.save(allergen);
    }

    // Ta bort allergen
    public void delete(Long id) {
        Allergen allergen = findById(id);
        allergenRepository.delete(allergen);
    }

    // Hitta kritiska allergener
    public List<Allergen> findCriticalAllergens() {
        return allergenRepository.findBySeverityOrderByNameAsc(AllergenSeverity.CRITICAL);
    }

    // Hitta allergener med viss severity
    public List<Allergen> findBySeverity(AllergenSeverity severity) {
        return allergenRepository.findBySeverity(severity);
    }
}
