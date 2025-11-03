package org.example.repository;

import org.example.entities.Catch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface CatchRepository extends JpaRepository<Catch, Long> {

   List<Catch> findBySpecies(String species);

}
