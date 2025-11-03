package org.example.service.director;

import org.example.dto.request.director.CreateDirectorRequest;
import org.example.dto.request.director.UpdateDirectorRequest;
import org.example.entities.Director;

import java.util.List;

public interface DirectorService {
    Director addDirector(Director director);
    Director getDirector(Long id);
    Director updateDirector(Long id, Director director);
    void deleteDirector(Long id);
    List<Director> getAllDirectors();
}
