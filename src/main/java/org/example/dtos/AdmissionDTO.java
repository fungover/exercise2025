package org.example.dtos;

import java.time.LocalDateTime;

public record AdmissionDTO(Long admId, Long patId, LocalDateTime dateIn, LocalDateTime dateOut, String diagnosis, String department) {
}
