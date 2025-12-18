package org.example.controller;

import org.example.dto.PatientDTO;
import org.example.entity.Patient;
import org.example.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

	@PostMapping("/api/patients/new")
	public String newPatient(@ModelAttribute Patient pat) {
		patientRepository.save(pat);
		return "redirect:/patients";
	}

	@GetMapping("/api/patients/{id}")
	public PatientDTO findPatientById(@PathVariable java.lang.Long id) {
		var pat = patientRepository.findById(id);
		if (pat.isEmpty()) {
			throw new IllegalArgumentException("Patient with id " + id + " not found");
		}
		Patient foundPat = pat.get();
		return new PatientDTO(foundPat.getFirstName(), foundPat.getLastName(), foundPat.getAddress(), foundPat.getDateOfBirth());
	}
}
