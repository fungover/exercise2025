package org.example.dto;

import java.util.List;

public record User(Long id, String name, String password, String email, List<Note> notes) {
}
