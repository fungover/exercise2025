package org.example.repository;

import org.example.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
  List<NoteEntity> findByUserId(Long userId);

  Optional<NoteEntity> findByIdAndUserId(Long id, Long userId);

  @Query(value = "SELECT * FROM note WHERE value LIKE CONCAT('%', :value, '%')", nativeQuery = true)
  Optional<NoteEntity> findNote(String value);
}