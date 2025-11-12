package org.example.repositories;

import org.example.entities.Patient;
import org.springframework.data.repository.ListCrudRepository;

public interface PatientRepository extends ListCrudRepository<Patient, Long> {
}
