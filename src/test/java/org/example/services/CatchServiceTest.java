package org.example.services;

import org.example.DTO.CreateCatchDTO;
import org.example.entities.Catch;
import org.example.repository.CatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CatchServiceTest {

    private CatchRepository catchRepository;
    private SanitizationService sanitizationService;
    private CatchService service;

    @BeforeEach
    void Setup() {
        catchRepository = mock(CatchRepository.class);
        sanitizationService = mock(SanitizationService.class);
        service = new CatchService(catchRepository, sanitizationService);
    }

    @Test
    void DetAllCatches_returnsList() {
        when(catchRepository.findAll()).thenReturn(List.of(new Catch("Pike", 100.0, 25.0)));
        List<Catch> result = service.getAllCatches();
        assertEquals(1, result.size());
        assertEquals("Pike", result.get(0).getSpecies());
    }

    @Test
    void CreateCatch_sanitizesAndSaves() {
        CreateCatchDTO dto = new CreateCatchDTO("Pike<script>", 100.0, 25.0);
        when(sanitizationService.sanitize(anyString())).thenReturn("Pike");
        when(catchRepository.save(any(Catch.class))).thenAnswer(i -> i.getArgument(0));

        Catch result = service.createCatch(dto);

        assertEquals("Pike", result.getSpecies());
        verify(sanitizationService).sanitize("Pike<script>");
        verify(catchRepository).save(any(Catch.class));
    }

    @Test
    void DeleteCatch_existing_returnsTrue() {
        when(catchRepository.existsById(1L)).thenReturn(true);
        boolean result = service.deleteCatch(1L);
        assertTrue(result);
        verify(catchRepository).deleteById(1L);
    }

    @Test
    void DeleteCatch_missing_returnsFalse() {
        when(catchRepository.existsById(99L)).thenReturn(false);
        boolean result = service.deleteCatch(99L);
        assertFalse(result);
        verify(catchRepository, never()).deleteById(any());
    }
}
