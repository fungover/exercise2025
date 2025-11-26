package org.example.repository;

import org.example.entity.Admission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface AdmissionRepository extends ListCrudRepository<Admission, Long> {

	@Query("""
					select admission from Admission admission
					""")
	List<Admission> getAllAdmissions();

	@Query("""
						select admission from Admission admission where admission.admission_id = :id
					""")
	Admission getAdmissionById(Long id);

	@Query("""
						select admission from Admission admission
						join Patient pat on admission.patient.pat_id = :patId 
					""")
	public List<Admission> getAdmissionsByPatId(Long patId);
}
