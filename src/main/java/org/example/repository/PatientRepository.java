package org.example.repository;

import org.example.entity.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface PatientRepository extends ListCrudRepository<Patient, java.lang.Long> {

	@Query("""
					select pat from Patient pat where pat.ssn = :ssn
					""")
	public Patient findBySsn(String ssn);

}
