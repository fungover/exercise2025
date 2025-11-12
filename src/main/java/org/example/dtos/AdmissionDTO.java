package org.example.dtos;

import java.time.LocalDateTime;

public record AdmissionDTO(Long patientId, LocalDateTime dateIn,LocalDateTime dateOut, String diagnosis, String department) {
}
