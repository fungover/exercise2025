package org.example.repositories;

import org.example.entities.Admission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface AdmissionRepository extends ListCrudRepository<Admission, Long> {

	@Query("""
					select admission from Admission admission
					""")
	List<Admission> getAllAdmissions();
}
