package org.example;

import org.example.entities.Admission;
import org.springframework.data.repository.ListCrudRepository;

public interface AdmissionRepository extends ListCrudRepository<Admission, Long> {
}
