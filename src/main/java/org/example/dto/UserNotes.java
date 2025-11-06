package org.example.dto;

import java.util.List;

public record UserNotes(Long userId, String name, List<Note> notes) {
}
