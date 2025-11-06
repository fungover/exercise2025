package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.Note;
import org.example.entity.ApiEntity;
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
    userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

    List<NoteEntity> userNotes = noteRepository.findByUserId(userId);

    return userNotes.stream()
            .map(note -> new Note(
                    note.getId(),
                    note.getValue(),
                    note.getUser().getId(),
                    note.getCreatedAt()
            ))
            .toList();
  }

  @Transactional
  public Note createNewUserNote(Note note, Long userId, String apiKey) {
    UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId)
    );

    ApiEntity apiEntity = user.getApiKey();
    if(apiEntity == null || !apiEntity.getApiKey().equals(apiKey)){
      throw new SecurityException("API key not valid for user with id: " + userId + " or invalid API key provided: " + apiKey + " (expected: " + apiEntity.getApiKey() + "");
    }

    var savedEntity = noteRepository.save(new NoteEntity(null, user, note.value(), null));
    return new Note(
            savedEntity.getId(),
            savedEntity.getValue(),
            savedEntity.getUser().getId(),
            savedEntity.getCreatedAt()
    );
  }

  @Transactional
  public Note deleteNote(Long noteId, Long userId, String apiKey) {
    NoteEntity noteToDelete = noteRepository.findByIdAndUserId(noteId, userId)
            .orElseThrow(() -> new EntityNotFoundException("Note not found with id: " + noteId + " for user with id: " + userId));
    noteRepository.delete(noteToDelete);

    ApiEntity apiEntity = noteToDelete.getUser().getApiKey();
    if(apiEntity == null || !apiEntity.getApiKey().equals(apiKey)){
      throw new SecurityException("API key not valid for user with id: " + userId + " or invalid API key provided: " + apiKey + " (expected: " + apiEntity.getApiKey() + "");
    }

    return new Note(noteToDelete.getId(),
            noteToDelete.getValue(),
            noteToDelete.getUser().getId(),
            noteToDelete.getCreatedAt()
    );

  }
}

