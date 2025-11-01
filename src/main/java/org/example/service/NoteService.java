package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.Note;
import org.example.entity.NoteEntity;
import org.example.entity.UserEntity;
import org.example.repository.NoteRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {
  private final UserRepository userRepository;
  private final NoteRepository noteRepository;

  public NoteService(UserRepository userRepository, NoteRepository noteRepository) {
    this.userRepository = userRepository;
    this.noteRepository = noteRepository;

  }

  @Transactional(readOnly = true)
  public List<Note> getNotes(Long userId) {
    return noteRepository.findByUserId(userId).stream()
            .map(note -> new Note(note.getId(), note.getValue(), userId, note.getCreatedAt())).toList();
  }

  @Transactional
  public Note createNewUserNote(Note note) {
    UserEntity user = userRepository.findById(note.userId())
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + note.userId())
    );

    var savedEntity = noteRepository.save(new NoteEntity(null, user, note.value(), null));
    return new Note(
            savedEntity.getId(),
            savedEntity.getValue(),
            savedEntity.getUser().getId(),
            savedEntity.getCreatedAt()
    );

  }
}

