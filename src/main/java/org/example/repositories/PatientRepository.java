package org.example.repositories;

import org.example.entities.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface PatientRepository extends ListCrudRepository<Patient, Long> {

	@Query("""
					select pat from Patient pat where  pat.pat_id = :id
					""")
	public Patient findPatientById(Long id);

	@Query("""
						select pat from Patient pat where concat(pat.firstName, ' ', pat.lastName) = concat(:first, ' ', :last)
					""")
	public Patient findByFirstAndLastName(String first, String last);

	@Query("""
					select pat from Patient pat where pat.ssn = :ssn
					""")
	public Patient findBySsn(String ssn);

}
