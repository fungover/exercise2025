package org.example.controller;

import org.example.dto.Note;
import org.example.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoteController {
  private final NoteService noteService;

  public NoteController(NoteService noteService) {
    this.noteService = noteService;
  }

  @GetMapping("/notes/{userId}")
  public ResponseEntity<List<Note>> getNotes(@PathVariable Long userId) {
    return ResponseEntity.ok(noteService.getNotes(userId));
  }
}
