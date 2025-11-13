package org.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pat_id;

	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String address;

	@NotNull
	private LocalDate dateOfBirth;
	private String ssn;

	public Patient() {}

	public Patient(LocalDate dateOfBirth, String address, String lastName, String firstName, String ssn) {
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.lastName = lastName;
		this.firstName = firstName;
		this.ssn = ssn;
	}

	// -- Getter --
	public Long getId() {
		return pat_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public String getSsn() {
		return ssn;
	}
}
