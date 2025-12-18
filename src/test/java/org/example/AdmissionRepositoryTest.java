package org.example;

import org.example.entity.Admission;
import org.example.entity.Patient;
import org.example.repository.AdmissionRepository;
import org.example.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class AdmissionRepositoryTest {

    @Autowired
    private AdmissionRepository admissionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void getAdmissionsByPatId_returns_only_admissions_for_that_patient() {
        Patient patient1 = new Patient(
                LocalDate.of(1990, 1, 1),
                "Stockholm",
                "Doe",
                "John",
                "19900101-1234"
        );

        Patient patient2 = new Patient(
                LocalDate.of(1985, 5, 5),
                "Gothenburg",
                "Smith",
                "Jane",
                "19850505-5678"
        );

        patientRepository.save(patient1);
        patientRepository.save(patient2);

        Admission a1 = new Admission(
                patient1, "Flu", "General", LocalDateTime.now()
        );
        Admission a2 = new Admission(
                patient1, "Cold", "General", LocalDateTime.now()
        );
        Admission a3 = new Admission(
                patient2, "Broken arm", "Emergency", LocalDateTime.now()
        );

        admissionRepository.saveAll(List.of(a1, a2, a3));

        List<Admission> result =
                admissionRepository.getAdmissionsByPatId(patient1.getPatId());

        assertThat(result).hasSize(2);
        assertThat(result)
                .allMatch(adm -> adm.getPatient().getPatId().equals(patient1.getPatId()));
    }
}

