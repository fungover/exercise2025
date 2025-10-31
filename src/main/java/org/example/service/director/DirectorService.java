package org.example.service.director;

import org.example.dto.request.director.CreateDirectorRequest;
import org.example.dto.request.director.UpdateDirectorRequest;
import org.example.dto.response.director.DirectorResponse;
import org.example.entities.Director;

public interface DirectorService {
    DirectorResponse addDirector(CreateDirectorRequest director);
    DirectorResponse updateDirector(UpdateDirectorRequest director);
    DirectorResponse getDirector(Long id);
    void deleteDirector(Long id);
}
