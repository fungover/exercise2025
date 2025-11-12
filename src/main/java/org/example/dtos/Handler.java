package org.example.dtos;

import org.example.entities.Patient;

public record Handler(Patient patient, NewAdmissionDTO admission) {
}
