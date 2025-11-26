package org.example.controller;

import org.example.dto.PatientDTO;
import org.example.entity.Patient;
import org.example.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class PatientsController {

	@Autowired
	PatientRepository patientRepository;


	// ---- PATIENTS ----

	@GetMapping("/patients-menu")
	public String patientsMenu(Model model) {
		return "patients/patientsMenu";
	}

	@GetMapping("/patients")
	public String displayPatients(Model model) {
		model.addAttribute("patients", patientRepository.findAll().stream()
						.map(pat -> new PatientDTO(pat.getFirstName(), pat.getLastName(), pat.getAddress(), pat.getDateOfBirth()))
						.toList());
		return "patients/patients";
	}

	@GetMapping("/patients/new")
	public String newPatient(Model model) {
		model.addAttribute("patient", new Patient());
		return "patients/newPatient";
	}
}
