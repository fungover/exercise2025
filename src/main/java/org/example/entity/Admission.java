package org.example.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Admission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private java.lang.Long admission_id;

	private LocalDateTime dateIn;
	private LocalDateTime dateOut = null;

	private String diagnosis;
	private String department;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pat_id")
	Patient patient;

	public Admission() {}

	public Admission(Patient patient, String diagnosis, String department, LocalDateTime dateIn) {
		this.patient = patient;
		this.diagnosis = diagnosis;
		this.department = department;
		this.dateIn = dateIn;
	}

	// -- Getter
	public java.lang.Long getAdmission_id() {
		return admission_id;
	}

	public LocalDateTime getDateIn() {
		return dateIn;
	}

	public LocalDateTime getDateOut() {
		return dateOut;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public String getDepartment() {
		return department;
	}

	public Patient getPatient() {
		return patient;
	}

	// -- Setter --

	public void setDateOut(LocalDateTime dateOut) {
		this.dateOut = dateOut;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
