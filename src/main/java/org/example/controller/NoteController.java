package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.Note;
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
    return ResponseEntity.ok(noteService.getNotes(userId));
  }

  @PostMapping("/notes")
  public ResponseEntity<Note> createNote(@Valid @RequestBody Note note) {
    log.info("Received note: {}", note);
    return ResponseEntity.status(201).body(noteService.createNewUserNote(note));
  }
}
