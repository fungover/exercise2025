package org.example.config;

import jakarta.transaction.Transactional;
import org.example.entity.Admission;
import org.example.entity.Patient;
import org.example.repository.AdmissionRepository;
import org.example.repository.PatientRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile({"dev", "default"})
public class DataInitializer implements ApplicationRunner {

	private final AdmissionRepository admissionRepository;
	private final PatientRepository patientRepository;

	public DataInitializer(AdmissionRepository admissionRepository, PatientRepository patientRepository) {
		this.admissionRepository = admissionRepository;
		this.patientRepository = patientRepository;
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		boolean forceInit = args.containsOption("force-init");
		if (forceInit || admissionRepository.count() == 0) {
			var patient1 = new Patient(LocalDate.of(1978, 12, 3), "Rome", "Vance", "Liora", "19781303-8912");
			var patient2 = new Patient(LocalDate.of(1997, 6, 13), "Venice", "Mendel", "Tarek", "19970613-1397");
			var patient3 = new Patient(LocalDate.of(2001, 7, 13), "Florence", "Holt", "Cassian", "20010713-9721");
			patientRepository.saveAll(List.of(patient1, patient2, patient3));

			var admission1 = new Admission(patient2, "Pneumonia", "Infection", LocalDateTime.now());
			var admission2 = new Admission(patient1, "Heart thing", "HIA", LocalDateTime.now());
			var admission3 = new Admission(patient3, "Corona", "Emergency", LocalDateTime.now());
			admissionRepository.saveAll(List.of(admission1, admission2, admission3));
		}
	}
}
