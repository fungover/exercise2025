package org.example.controllers;

import org.example.dtos.AdmissionDTO;
import org.example.dtos.PatientDTO;
import org.example.repositories.AdmissionRepository;
import org.example.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
	@Autowired
	AdmissionRepository admissionRepository;
	@Autowired
	PatientRepository patientRepository;

	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}

	@GetMapping("/patients")
	public String displayPatients(Model model) {
		model.addAttribute("patients", patientRepository.findAll().stream()
						.map(pat -> new PatientDTO(pat.getFirstName(), pat.getLastName(), pat.getAddress(), pat.getDateOfBirth()))
						.toList());
		return "patients";
	}

	@GetMapping("/admissions")
	public String displayAdmissions(Model model) {
		model.addAttribute("admissions", admissionRepository.findAll().stream()
						.map(adm -> new AdmissionDTO(adm.getPatient().getId(), adm.getDateIn(), adm.getDateOut(), adm.getDiagnosis(), adm.getDepartment()))
						.toList());
		return "admissions";
	}

}
