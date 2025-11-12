package org.example.controllers;

import org.example.dtos.AdmissionDTO;
import org.example.dtos.NewAdmissionDTO;
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

	@GetMapping("patients")
	public List<PatientDTO> findAllPatients() {
		return patientRepository.findAll().stream()
						.map(pat -> new PatientDTO(pat.getFirstName(), pat.getLastName(), pat.getAddress(), pat.getDateOfBirth()))
						.toList();
	}

	@GetMapping("admissions")
	public List<AdmissionDTO> findAllAdmissions() {
		return admissionRepository.getAllAdmissions().stream()
						.map(adm -> new AdmissionDTO(adm.getPatient().getId(),
										adm.getDateIn(), adm.getDateOut(),
										adm.getDiagnosis(), adm.getDepartment()))
						.toList();
	}

	@PostMapping("newPat")
	public void handleNew(@RequestBody Handler handler) {
		var adm = handler.admission();
		var pat = handler.patient();
		Patient newPat = new Patient(pat.getDateOfBirth(), pat.getAddress(), pat.getLastName(),pat.getFirstName());
		patientRepository.save(newPat);

		admissionRepository.save(new Admission(newPat, adm.diagnosis(), adm.department()));
	}
}
