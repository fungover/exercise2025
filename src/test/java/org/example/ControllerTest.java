package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Admission;
import org.example.entity.Patient;
import org.example.repository.AdmissionRepository;
import org.example.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
class ControllerTest {

	@MockitoBean
	AdmissionRepository admissionRepository;

	@MockitoBean
	PatientRepository patientRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
	void getAllAdmissions() throws Exception {
		when(admissionRepository.findAll())
						.thenReturn(List.of(
										new Admission(new Patient(), "Pneumonia", "Infection"),
										new Admission(new Patient(), "Heart Thing", "HIA"),
										new Admission(new Patient(), "Corona", "Emergency")
						));

		mockMvc.perform(get("/admissions"))
						.andExpect(status().isOk());
	}

	@Test
	void getAllPatientsUnauthorized() throws Exception {
		mockMvc.perform(get("/patients"))
						.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
	void getAllPatientsAuthenticated() throws Exception {
		mockMvc.perform(get("/patients"))
						.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
	void createANewPatient() throws Exception {

		Patient patient = new Patient(LocalDate.of(2000, 1, 10), "Address", "LastName", "FirstName", "ssn");

		mockMvc.perform(post("/api/patients/new")
						.with(csrf())
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("firstName", "FirstName")
						.param("lastName", "LastName")
						.param("address", "Address")
						.param("dateOfBirth", "2000-01-10")
						.param("ssn", "ssn"))
				.andExpect(status().is3xxRedirection());
	}

}
