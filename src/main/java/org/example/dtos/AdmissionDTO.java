package org.example.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AdmissionDTO(Long patId, LocalDateTime dateIn, LocalDateTime dateOut, String diagnosis, String department) {
}
