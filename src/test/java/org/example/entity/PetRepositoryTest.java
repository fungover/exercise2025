package org.example.entity;

import org.example.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PetRepositoryTest {

    @Autowired
    PetRepository repo;

    @Test
    void persistAndFind(){
        Pet p = Pet.builder().name("Test").species("Cat").build();
        Pet saved = repo.save(p);
        assertThat(saved.getId()).isNotNull();
        assertThat(repo.findById(saved.getId()).get().getName()).isEqualTo("Test");
    }

    @Test
    void findAll() {
        repo.save(Pet.builder().name("AA").species("B").build());
        assertThat(repo.findAll()).hasSize(1);
    }
}
