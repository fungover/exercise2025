package org.example.controllers;

import org.example.dtos.AdmissionDTO;
import org.example.dtos.Handler;
import org.example.dtos.PatientDTO;
import org.example.entities.Admission;
import org.example.entities.Patient;
import org.example.repositories.AdmissionRepository;
import org.example.repositories.PatientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class HospitalController {
	private final AdmissionRepository admissionRepository;
	private final PatientRepository patientRepository;

	public HospitalController(AdmissionRepository admissionRepository, PatientRepository patientRepository) {
		this.admissionRepository = admissionRepository;
		this.patientRepository = patientRepository;
	}

	// ---- PATIENTS ----

	@GetMapping("patients")
	public List<PatientDTO> findAllPatients() {
		return patientRepository.findAll().stream()
						.map(pat -> new PatientDTO(pat.getFirstName(), pat.getLastName(), pat.getAddress(), pat.getDateOfBirth()))
						.toList();
	}

	@GetMapping("patients/{id}")
	public PatientDTO findPatientById(@PathVariable Long id) {
		var pat = patientRepository.findById(id);
		if  (pat.isEmpty()) {
			throw new IllegalArgumentException("Patient with id " + id + " not found");
		}
		Patient foundPat = pat.get();
		return new PatientDTO(foundPat.getFirstName(), foundPat.getLastName(), foundPat.getAddress(), foundPat.getDateOfBirth());
	}


	// ---- Admissions ----

	@GetMapping("admissions")
	public List<AdmissionDTO> findAllAdmissions() {
		return admissionRepository.getAllAdmissions().stream()
						.map(adm -> new AdmissionDTO(adm.getPatient().getId(),
										adm.getDateIn(),
										adm.getDateOut(),
										adm.getDiagnosis(),
										adm.getDepartment()))
						.toList();
	}

	@PostMapping("admissions/new")
	public void handleNew(@RequestBody Handler handler) {
		var adm = handler.admission();
		var patInput = handler.patient();
		Patient pat;
		pat = patientRepository.findBySsn(patInput.getSsn());
		if (pat == null) {
			pat = new Patient(patInput.getDateOfBirth(),
							patInput.getAddress(),
							patInput.getLastName(),
							patInput.getFirstName(),
							patInput.getSsn());
			patientRepository.save(pat);
		}

		admissionRepository.save(new Admission(pat, adm.diagnosis(), adm.department()));
	}

	@PatchMapping("admissions/{id}/writeOut")
	public void writeOut(@PathVariable Long id) {
		var admission = admissionRepository.getAdmissionById(id);
		if (admission != null) {
			admission.setDateOut();
			admissionRepository.save(admission);
		}
	}

	@GetMapping("admissions/{pat_id}")
	public List<AdmissionDTO> findAllPatientsByPatId(@PathVariable Long pat_id) {
		return admissionRepository.getAdmissionsByPatId(pat_id).stream()
						.map(adm -> new AdmissionDTO(adm.getPatient().getId(), adm.getDateIn(), adm.getDateOut(), adm.getDiagnosis(), adm.getDepartment()))
						.toList();
	}

}
