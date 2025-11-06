package org.example.service.director;

import org.example.entities.Director;
import org.example.repository.director.DirectorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DirectorServiceImplTest {

    @Mock
    private DirectorRepository directorRepository;

    @InjectMocks
    private DirectorServiceImpl directorService;

    @Test
    void addDirectorShouldSaveDirector() {
        Director director = new Director("James", "Gunn", List.of());

        when(directorRepository.save(director)).thenReturn(director);
        Director savedDirector = directorService.addDirector(director);

        assertEquals("James", savedDirector.getFirstName());
        assertEquals("Gunn", savedDirector.getLastName());

        verify(directorRepository).save(director);
    }

}
