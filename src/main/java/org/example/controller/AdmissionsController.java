package org.example.controller;

import org.example.dto.AdmissionDTO;
import org.example.dto.NewAdmissionDTO;
import org.example.entity.Admission;
import org.example.repository.AdmissionRepository;
import org.example.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdmissionsController {

	@Autowired
	AdmissionRepository admissionRepository;
	@Autowired
	PatientRepository patientRepository;

	// ---- ADMISSIONS ----

	@GetMapping("/admissions-menu")
	public String displayPatientMenu(Model model) {
		return "admissions/admissionMenu";
	}

	@GetMapping("/admissions")
	public String displayAdmissions(Model model) {
		model.addAttribute("admissions", admissionRepository.findAll().stream()
						.map(adm -> new AdmissionDTO(adm.getAdmission_id(), adm.getPatient().getPatId(), adm.getDateIn(), adm.getDateOut(), adm.getDiagnosis(), adm.getDepartment()))
						.toList());
		return "admissions/admissions";
	}

	@GetMapping("/admissions/new")
	public String newAdmission(Model model) {
		model.addAttribute("admission", new NewAdmissionDTO(null, null, null));
		return "admissions/newAdmission";
	}

	// ----

	@PostMapping("/api/admissions/new")
	public String handleNew(@ModelAttribute NewAdmissionDTO newAdm) {

		var pat = patientRepository.findBySsn(newAdm.patSsn());
		if (pat == null) {
			throw new IllegalArgumentException("Patient with SSN " + newAdm.patSsn() + " not found");
		} else {
			admissionRepository.save(new Admission(pat, newAdm.diagnosis(), newAdm.department(), LocalDateTime.now()));
		}
		return "redirect:/admissions";
	}

	@PostMapping("/api/admissions/{id}/writeOut")
	public String writeOut(@PathVariable java.lang.Long id) {
		Admission admission = admissionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Admission not found"));
		admission.setDateOut(LocalDateTime.now());
		admissionRepository.save(admission);
		return "redirect:/admissions";
	}

	@GetMapping("/api/admissions/{pat_id}")
	public List<AdmissionDTO> findAllPatientsByPatId(@PathVariable java.lang.Long pat_id) {
		return admissionRepository.getAdmissionsByPatId(pat_id).stream()
				.map(adm -> new AdmissionDTO(adm.getAdmission_id(), adm.getPatient().getPatId(), adm.getDateIn(), adm.getDateOut(), adm.getDiagnosis(), adm.getDepartment()))
				.toList();
	}
}
