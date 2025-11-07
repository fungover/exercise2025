package org.example.dto;

import java.util.List;

public record UserNotes(String name, List<NoteResponse> notes) {
}
