package org.example.service.director;

import org.example.entities.Director;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repository.director.DirectorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Transactional
    @Override
    public Director addDirector(Director director) {
        return directorRepository.save(director);
    }

    @Override
    public Director getDirector(Long id) {
        return directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found with id: " + id));
    }

    @Transactional
    @Override
    public Director updateDirector(Long id, Director director) {
        Director existingDirector = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found with id: " + id));

        if (director.getFirstName() != null) {
            existingDirector.setFirstName(director.getFirstName());
        }

        if (director.getLastName() != null) {
            existingDirector.setLastName(director.getLastName());
        }

        return directorRepository.save(existingDirector);
    }

    @Transactional
    @Override
    public void deleteDirector(Long id) {
        directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found with id: " + id));

        directorRepository.deleteById(id);
    }

    @Override
    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }
}
