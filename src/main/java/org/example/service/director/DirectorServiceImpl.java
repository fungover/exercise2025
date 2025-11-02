package org.example.service.director;

import org.example.entities.Director;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repository.director.DirectorRepository;
import org.springframework.stereotype.Service;

@Service
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public Director addDirector(Director director) {
        return directorRepository.save(director);
    }

    @Override
    public Director updateDirector(Director director) {
        return null;
    }

    @Override
    public Director getDirector(Long id) {
        return directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found with id" + id));
    }

    @Override
    public void deleteDirector(Long id) {
        directorRepository.deleteById(id);
    }
}
