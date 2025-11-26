package org.example.controller;

import org.example.dto.AdmissionDTO;
import org.example.dto.NewAdmissionDTO;
import org.example.repository.AdmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdmissionsController {

	@Autowired
	AdmissionRepository admissionRepository;

	// ---- ADMISSIONS ----

	@GetMapping("/admissions-menu")
	public String displayPatientMenu(Model model) {
		return "admissions/admissionMenu";
	}

	@GetMapping("/admissions")
	public String displayAdmissions(Model model) {
		model.addAttribute("admissions", admissionRepository.findAll().stream()
						.map(adm -> new AdmissionDTO(adm.getAdmission_id(), adm.getPatient().getId(), adm.getDateIn(), adm.getDateOut(), adm.getDiagnosis(), adm.getDepartment()))
						.toList());
		return "admissions/admissions";
	}

	@GetMapping("/admissions/new")
	public String newAdmission(Model model) {
		model.addAttribute("admission", new NewAdmissionDTO(null, null, null));
		return "admissions/newAdmission";
	}
}
