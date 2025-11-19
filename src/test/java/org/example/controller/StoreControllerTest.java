package org.example.controller;

import org.example.entities.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StoreControllerTest extends TestSetup{

    @Autowired
    TestRestTemplate restTemplate;

    private TestRestTemplate loggedInUser(){
        return restTemplate.withBasicAuth("admin", "admin");
    }

    @Test
    void getStores() {
        ResponseEntity<String> response = loggedInUser().getForEntity("/api/stores", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldFindAllStores() {
        Store [] stores = loggedInUser().getForObject("/api/stores", Store[].class);
        assertThat(stores.length).isEqualTo(2);
    }

    @Test
    void getBooksByAuthor() {
    }

    @Test
    void getBooksByName() {
    }
}