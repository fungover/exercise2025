package org.example.service;

import org.example.dto.PetDTO;
import org.example.entity.Pet;
import org.example.exception.PetNotFoundException;
import org.example.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PetServiceTest {

    @Autowired
    PetService service;
    @Autowired
    PetRepository repo;

    @Test
    void adoptAndGet() {
        Pet input = Pet.builder().name("Hugo").species("Dog").build();
        PetDTO dto = service.adopt(input);
        assertThat(dto.name()).isEqualTo("Hugo");
        assertThat(service.get(dto.id()).species()).isEqualTo("Dog");
    }

    @Test
    void listAll() {
        repo.save(Pet.builder().name("AA").species("B").build());
        assertThat(service.listAll()).hasSize(1);
    }

    @Test
    void getThrowsNotFound() {
        assertThatThrownBy(() -> service.get(999L)).isInstanceOf(PetNotFoundException.class);
    }

    @Test
    void feedAndPlay() {
        Pet p = repo.save(Pet.builder().name("CC").species("DD").hungerLevel(60).happiness(40).build());
        PetDTO fed = service.feed(p.getId());
        assertThat(fed.hungerLevel()).isEqualTo(50);
        PetDTO played = service.play(p.getId());
        assertThat(played.happiness()).isEqualTo(50);
    }

    @Test
    void release() {
        Pet p = repo.save(Pet.builder().name("EE").species("F").build());
        service.release(p.getId());
        assertThat(repo.findById(p.getId())).isEmpty();
    }
}