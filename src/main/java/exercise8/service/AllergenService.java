package exercise8.service;

import exercise8.entity.Allergen;
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

    // Get all allergens
    public List<Allergen> findAll() {
        return allergenRepository.findAll();
    }

    // Get allergen via ID
    public Allergen findById(Long id) {
        return allergenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allergen", "id", id));
    }

    // Create new allergen
    public Allergen create(Allergen allergen) {
        return allergenRepository.save(allergen);
    }

    // Update allergen
    public Allergen update(Long id, Allergen allergenDetails) {
        Allergen allergen = findById(id);

        allergen.setName(allergenDetails.getName());
        // allergen.setDescription(allergenDetails.getDescription()); ✂️ BORTTAGET!

        return allergenRepository.save(allergen);
    }

    // Delete allergen
    public void delete(Long id) {
        Allergen allergen = findById(id);
        allergenRepository.delete(allergen);
    }
}