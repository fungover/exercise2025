package org.example.dtos;

import org.example.entities.Patient;

import java.time.LocalDateTime;

public record AdmissionDTO(Long patId, LocalDateTime dateIn, LocalDateTime dateOut, String diagnosis, String department) {
}
