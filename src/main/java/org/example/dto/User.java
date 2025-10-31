package org.example.dto;

import java.util.List;

public record User(String name, String passwordHashed, String email, List<Note> notes) {
}
