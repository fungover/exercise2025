package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.PetDTO;
import org.example.entity.Pet;
import org.example.service.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(PetRestController.class)
class PetRestControllerTest {
    @Autowired
    MockMvc mvc;
    @MockitoBean
    PetService service;
    @Autowired
    ObjectMapper mapper;

    @Test
    void list() throws Exception {
        when(service.listAll()).thenReturn(List.of(new PetDTO(1L, "AA", "B", 50, 50, null, null)));
        mvc.perform(MockMvcRequestBuilders.get("/pets")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void adopt() throws Exception {
        PetDTO dto = new PetDTO(1L, "CC", "D", 50, 50, null, null);
        when(service.adopt(any())).thenReturn(dto);
        mvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Pet(null, "CC", "D", 50, 50, null, null))))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("CC"));
    }

    @Test
    void get() throws Exception {
        PetDTO dto = new PetDTO(1L, "EE", "F", 50, 50, null, null);
        when(service.get(1L)).thenReturn(dto);
        mvc.perform(MockMvcRequestBuilders.get("/pets/{id}", 1L)).andExpect(status().isOk()).andExpect(jsonPath("$.species").value("F"));
    }

    @Test
    void feed() throws Exception {
        PetDTO dto = new PetDTO(1L, "GG", "H", 100, 50, null, null);
        when(service.feed(1L)).thenReturn(dto);
        mvc.perform(put("/pets/1/feed")).andExpect(status().isOk()).andExpect(jsonPath("$.hungerLevel").value(100));
    }

    @Test
    void play() throws Exception {
        PetDTO dto = new PetDTO(1L, "II", "J", 40, 100, null, null);
        when(service.play(1L)).thenReturn(dto);
        mvc.perform(put("/pets/1/play")).andExpect(status().isOk()).andExpect(jsonPath("$.happiness").value(100));
    }

    @Test
    void release() throws Exception {
        doNothing().when(service).release(1L);
        mvc.perform(delete("/pets/1")).andExpect(status().isNoContent());
    }
}