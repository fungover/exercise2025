package org.example.controllers;

import org.example.dtos.AdmissionDTO;
import org.example.dtos.PatientDTO;
import org.example.repositories.AdmissionRepository;
import org.example.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
						.map(pat -> new PatientDTO(pat.getFirstName(), pat.getLastName(), pat.getAddress()))
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
}
