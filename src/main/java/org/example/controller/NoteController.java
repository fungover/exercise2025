package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.user.ApiPrincipal;
import org.example.dto.note.Note;
import org.example.dto.note.NoteNew;
import org.example.dto.user.UserNotes;
import org.example.service.NoteService;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NoteController {
  private final NoteService noteService;
  private final UserService userService;
  private static final Logger log = LoggerFactory.getLogger(NoteController.class);

  public NoteController(NoteService noteService, UserService userService) {
    this.noteService = noteService;
    this.userService = userService;
  }

  @PostMapping("/notes")
  public ResponseEntity<NoteNew> createNote(@Valid @RequestBody Note note,
                                            Authentication authentication) {

    ApiPrincipal apiPrincipal = (ApiPrincipal) authentication.getPrincipal();

    log.info("Received note: {}", note);
    var createNote = noteService.createNewUserNote(note, apiPrincipal.userId());
    log.info("Created note: {}", createNote);
    return ResponseEntity.ok(createNote);
  }

  @DeleteMapping("/notes/{noteId}")
  public ResponseEntity<Note> deleteNote(@PathVariable Long noteId,
                                         Authentication authentication) {

    ApiPrincipal apiPrincipal = (ApiPrincipal) authentication.getPrincipal();

    log.info("Received request to delete note with id: {} for user with id: {}", noteId, apiPrincipal.userId());
    var deleteNote = noteService.deleteNote(noteId, apiPrincipal.userId());
    log.info("Deleted note: {}", deleteNote);
    return ResponseEntity.ok(deleteNote);
  }

  @GetMapping("/notes/user")
  public ResponseEntity<UserNotes> getUserById(Authentication authentication) {
    ApiPrincipal apiPrincipal = (ApiPrincipal) authentication.getPrincipal();
    log.info("User: {} requested notes", apiPrincipal.userId());
    return ResponseEntity.ok(userService.findUserAndGetNotes(apiPrincipal.email()));
  }
}
