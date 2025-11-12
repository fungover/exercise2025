package org.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pat_id;

	private String firstName;
	private String lastName;
	private String address;

	private LocalDate dateOfBirth;

	public Patient() {}

	public Patient(LocalDate dateOfBirth, String address, String lastName, String firstName) {
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.lastName = lastName;
		this.firstName = firstName;
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
}
