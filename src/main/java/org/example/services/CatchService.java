package org.example.services;

import org.example.DTO.CatchYearDTO;
import org.example.DTO.CreateCatchDTO;
import org.example.entities.Catch;
import org.example.repository.CatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CatchService {

    private final CatchRepository catchRepository;
    private final SanitizationService sanitizationService;

    public CatchService(CatchRepository catchRepository,
                        SanitizationService sanitizationService) {
        this.catchRepository = catchRepository;
        this.sanitizationService = sanitizationService;
    }

    public List<Catch> getAllCatches() {
        return catchRepository.findAll();
    }

    public Optional<Catch> getCatchById(Long id) {
        return catchRepository.findById(id);
    }

    public long countCatches() {
        return catchRepository.count();
    }

    public List<CatchYearDTO> getCatchesOrderedByWeight(boolean stmt) {
        return stmt
                ? catchRepository.orderByWeightAsc()
                : catchRepository.orderByWeightDesc();
    }

    @Transactional
    public Catch createCatch(CreateCatchDTO dto) {
        // Sanitera species för att undvika XSS
        String sanitizedSpecies = sanitizationService.sanitize(dto.species());

        // Skapa Catch entity från DTO
        Catch catchEntity = new Catch();
        catchEntity.setSpecies(sanitizedSpecies);
        catchEntity.setWeight(dto.weight());
        catchEntity.setLength(dto.length());

        return catchRepository.save(catchEntity);
    }

    @Transactional
    public boolean deleteCatch(Long id) {
        if (!catchRepository.existsById(id)) {
            return false;
        }
        catchRepository.deleteById(id);
        return true;
    }
}