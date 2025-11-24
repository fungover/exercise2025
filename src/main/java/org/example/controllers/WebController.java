package org.example.controllers;

import org.example.dtos.AdmissionDTO;
import org.example.dtos.NewAdmissionDTO;
import org.example.dtos.PatientDTO;
import org.example.entities.Patient;
import org.example.repositories.AdmissionRepository;
import org.example.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
	@PreAuthorize("hasRole('USER')")
	public String displayPatients(Model model) {
		model.addAttribute("patients", patientRepository.findAll().stream()
						.map(pat -> new PatientDTO(pat.getFirstName(), pat.getLastName(), pat.getAddress(), pat.getDateOfBirth()))
						.toList());
		return "patients";
	}

	@GetMapping("/patients/new")
	@PreAuthorize("hasRole('USER')")
	public String newPatient(Model model) {
		model.addAttribute("patient", new Patient());
		return "newPatient";
	}

	@GetMapping("/admissions")
	@PreAuthorize("hasRole('ADMIN')")
	public String displayAdmissions(Model model) {
		model.addAttribute("admissions", admissionRepository.findAll().stream()
						.map(adm -> new AdmissionDTO(adm.getAdmission_id(), adm.getPatient().getId(), adm.getDateIn(), adm.getDateOut(), adm.getDiagnosis(), adm.getDepartment()))
						.toList());
		return "admissions";
	}

	@GetMapping("/admissions/new")
	@PreAuthorize("hasRole('ADMIN')")
	public String newAdmission(Model model) {
		model.addAttribute("admission", new NewAdmissionDTO(null, null, null));
		return "newAdmission";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@GetMapping("/logout")
	public String logout(Model model) {
		return "logout";
	}

}
