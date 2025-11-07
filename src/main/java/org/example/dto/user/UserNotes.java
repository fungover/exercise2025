package org.example.dto.user;

import org.example.dto.note.NoteResponse;

import java.util.List;

public record UserNotes(String name, List<NoteResponse> notes) {
}
