package org.example.service;

import org.example.dto.Note;
import org.example.repository.NoteRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
  private final UserRepository userRepository;
  private final NoteRepository noteRepository;

  public NoteService(UserRepository userRepository, NoteRepository noteRepository) {
    this.userRepository = userRepository;
    this.noteRepository = noteRepository;

  }

  public List<Note> getNotes(Long userId) {
    return noteRepository.findByUserId(userId).stream()
            .map(note -> new Note(note.getValue())).toList();
  }
}

