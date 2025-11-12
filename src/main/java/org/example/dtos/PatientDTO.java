package org.example.dtos;

import java.time.LocalDate;

public record PatientDTO(String firstName, String lastName, String address, LocalDate dob) {
}
