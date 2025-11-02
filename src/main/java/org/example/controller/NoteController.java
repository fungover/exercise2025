package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.example.dto.Note;
import org.example.entity.NoteEntity;
import org.example.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {
  private final NoteService noteService;
  private static final Logger log = LoggerFactory.getLogger(NoteController.class);

  public NoteController(NoteService noteService) {
    this.noteService = noteService;
  }

  @GetMapping("/notes/{userId}")
  public ResponseEntity<List<Note>> getNotes(@PathVariable Long userId) {
    log.info("Received request for notes for user: {}", userId);
    var notes = noteService.getNotes(userId);
    return ResponseEntity.ok(notes);
  }

  @PostMapping("/notes/{userId}")
  public ResponseEntity<Note> createNote(@Valid @RequestBody Note note, @PathVariable Long userId) {
    log.info("Received note: {}", note);
    var createNote = noteService.createNewUserNote(note, userId);
    log.info("Created note: {}", createNote);
    return ResponseEntity.ok(createNote);
  }

  @DeleteMapping("/notes/{userId}/{noteId}")
  public ResponseEntity<Note> deleteNote(@PathVariable Long userId, @PathVariable Long noteId) {
    log.info("Received request to delete note with id: {} for user with id: {}", noteId, userId);
    var deleteNote = noteService.deleteNote(noteId, userId);
    log.info("Deleted note: {}", deleteNote);
    return ResponseEntity.ok(deleteNote);
  }
}
