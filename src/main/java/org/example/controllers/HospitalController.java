package org.example.controllers;

import org.example.dtos.AdmissionDTO;
import org.example.dtos.NewAdmissionDTO;
import org.example.dtos.PatientDTO;
import org.example.entities.Admission;
import org.example.entities.Patient;
import org.example.repositories.AdmissionRepository;
import org.example.repositories.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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

	@PostMapping("patients/new")
	public String newPatient(@ModelAttribute Patient pat) {
		patientRepository.save(pat);
		return  "redirect:/patients";
	}

	@GetMapping("patients/{id}")
	public PatientDTO findPatientById(@PathVariable java.lang.Long id) {
		var pat = patientRepository.findById(id);
		if (pat.isEmpty()) {
			throw new IllegalArgumentException("Patient with id " + id + " not found");
		}
		Patient foundPat = pat.get();
		return new PatientDTO(foundPat.getFirstName(), foundPat.getLastName(), foundPat.getAddress(), foundPat.getDateOfBirth());
	}


	// ---- Admissions ----

	@GetMapping("admissions")
	public List<AdmissionDTO> findAllAdmissions() {
		return admissionRepository.getAllAdmissions().stream()
						.map(adm -> new AdmissionDTO(
										adm.getAdmission_id(),
										adm.getPatient().getId(),
										adm.getDateIn(),
										adm.getDateOut(),
										adm.getDiagnosis(),
										adm.getDepartment()))
						.toList();
	}

	@PostMapping("admissions/new")
	public String handleNew(@ModelAttribute NewAdmissionDTO newAdm) {

		Patient pat;
		pat = patientRepository.findBySsn(newAdm.patSsn());
		if (pat == null) {
			return "error";
		}
		else {
			admissionRepository.save(new Admission(pat, newAdm.diagnosis(), newAdm.department()));
			return "redirect:/admissions";
		}
	}

	@PatchMapping("admissions/{id}/writeOut")
	public void writeOut(@PathVariable java.lang.Long id) {
		var admission = admissionRepository.getAdmissionById(id);
		if (admission != null) {
			admission.setDateOut();
			admissionRepository.save(admission);
		}
	}

	@GetMapping("admissions/{pat_id}")
	public List<AdmissionDTO> findAllPatientsByPatId(@PathVariable java.lang.Long pat_id) {
		return admissionRepository.getAdmissionsByPatId(pat_id).stream()
						.map(adm -> new AdmissionDTO(adm.getAdmission_id(), adm.getPatient().getId(), adm.getDateIn(), adm.getDateOut(), adm.getDiagnosis(), adm.getDepartment()))
						.toList();
	}


/*
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
*/

}
