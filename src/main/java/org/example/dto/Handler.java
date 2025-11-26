package org.example.dto;

import org.example.entity.Patient;

public record Handler(Patient patient, NewAdmissionDTO admission) {
}
