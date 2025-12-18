package org.example.dto;

import org.example.entity.Patient;

public record PatientAdmissionRequest(Patient patient, NewAdmissionDTO admission) {
}
