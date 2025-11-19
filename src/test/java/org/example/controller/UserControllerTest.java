package org.example.controller;

import org.example.entities.CustomizedUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserControllerTest extends TestSetup {

    @Autowired
    TestRestTemplate restTemplate;

    private TestRestTemplate loggedInUser(){
        return restTemplate.withBasicAuth("admin", "admin");
    }

    @Test
    void newUserIsSuccessfullyCreated() {

        CustomizedUser newUser =  new CustomizedUser("Test", "Testar");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CustomizedUser> request = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> response = loggedInUser().exchange("/api/users", HttpMethod.POST, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}