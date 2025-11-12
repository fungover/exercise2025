package org.example.config;

import jakarta.transaction.Transactional;
import org.example.entities.Admission;
import org.example.entities.Patient;
import org.example.AdmissionRepository;
import org.example.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Profile({"dev", "default"})
public class DataInitializer implements ApplicationRunner {

	@Autowired
	private AdmissionRepository admissionRepository;
	@Autowired
	private PatientRepository patientRepository;

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		boolean forceInit = args.containsOption("force-init");
		if (forceInit || admissionRepository.count() == 0) {
			var patient1 = new Patient(LocalDate.of(1978, 12, 3), "Rome", "Vance", "Liora");
			var patient2 = new Patient(LocalDate.of(1997, 6, 13), "Venice", "Mendel", "Tarek");
			var patient3 = new Patient(LocalDate.of(2001, 7, 13), "Florence", "Holt", "Cassian");
			patientRepository.saveAll(List.of(patient1, patient2, patient3));

			var admission1 = new Admission(patient2, "Pneumonia", "Infection");
			var admission2 = new Admission(patient1, "Heart thing", "HIA");
			var admission3 = new Admission(patient3, "Autism", "High Security Ward");
			admissionRepository.saveAll(List.of(admission1, admission2, admission3));
		}
	}
}
