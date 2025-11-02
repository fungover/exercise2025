package org.example.service.director;

import org.example.dto.request.director.CreateDirectorRequest;
import org.example.dto.request.director.UpdateDirectorRequest;
import org.example.entities.Director;

public interface DirectorService {
    Director addDirector(Director director);
    Director updateDirector(Director director);
    Director getDirector(Long id);
    void deleteDirector(Long id);
}
