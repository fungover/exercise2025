package exercise8.service;

import exercise8.entity.Patrol;
import exercise8.exception.ResourceNotFoundException;
import exercise8.repository.PatrolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatrolService {

    @Autowired
    private PatrolRepository patrolRepository;

    // Hämta alla scoutkårer
    public List<Patrol> findAll() {
        return patrolRepository.findAll();
    }

    // Hämta scoutkår via ID
    public Patrol findById(Long id) {
        return patrolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patrol", "id", id));
    }

    // Skapa ny scoutkår
    public Patrol create(Patrol patrol) {
        return patrolRepository.save(patrol);
    }

    // Uppdatera scoutkår
    public Patrol update(Long id, Patrol patrolDetails) {
        Patrol patrol = findById(id);

        patrol.setName(patrolDetails.getName());
        patrol.setDistrict(patrolDetails.getDistrict());
        patrol.setContactPerson(patrolDetails.getContactPerson());
        patrol.setContactEmail(patrolDetails.getContactEmail());

        return patrolRepository.save(patrol);
    }

    // Ta bort scoutkår
    public void delete(Long id) {
        Patrol patrol = findById(id);
        patrolRepository.delete(patrol);
    }

    // Hitta scoutkårer i ett distrikt
    public List<Patrol> findByDistrict(String district) {
        return patrolRepository.findByDistrict(district);
    }
}
