package exercise8.service;

import exercise8.entity.Participant;
import exercise8.exception.ResourceNotFoundException;
import exercise8.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    // Get all participants
    public List<Participant> findAll() {
        return participantRepository.findAll();
    }

    // Retrieve participants via ID
    public Participant findById(Long id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participant", "id", id));
    }

    // Get participants via email
    public Participant findByEmail(String email) {
        return participantRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Participant", "email", email));
    }

    // Create new participant
    public Participant create(Participant participant) {
        return participantRepository.save(participant);
    }

    // Update participants
    public Participant update(Long id, Participant participantDetails) {
        Participant participant = findById(id);

        participant.setFirstName(participantDetails.getFirstName());
        participant.setLastName(participantDetails.getLastName());
        participant.setEmail(participantDetails.getEmail());
        participant.setPhone(participantDetails.getPhone());
        participant.setRoleGroup(participantDetails.getRoleGroup());
        participant.setPatrol(participantDetails.getPatrol());
        participant.setAllergens(participantDetails.getAllergens());

        return participantRepository.save(participant);
    }

    // Delete participants
    public void delete(Long id) {
        Participant participant = findById(id);
        participantRepository.delete(participant);
    }

    // Find participants in a scout troop
    public List<Participant> findByPatrolId(Long patrolId) {
        return participantRepository.findByPatrolId(patrolId);
    }

    // Search participants by name
    public List<Participant> searchByName(String searchTerm) {
        return participantRepository.searchByName(searchTerm);
    }
}

