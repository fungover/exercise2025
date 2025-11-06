package org.example.api;

import jakarta.validation.Valid;
import org.example.api.dto.PersonalBestCreate;
import org.example.api.dto.PersonalBestView;
import org.example.service.PersonalBestService;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pr")
public class PersonalBestController {
    private final PersonalBestService service;
    private final UserService users;

    public PersonalBestController(PersonalBestService s, UserService u) {
        this.service = s;
        this.users = u;
    }

    @GetMapping
    public List<PersonalBestView> myPrs(Authentication auth) {
        var userId = users.requireUserId(auth.getName());
        return service.listForUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonalBestView upsert(Authentication auth, @RequestBody @Valid PersonalBestCreate dto) {
        var userId = users.requireUserId(auth.getName());
        return service.upsert(userId, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Authentication auth, @PathVariable Long id) {
        var userId = users.requireUserId(auth.getName());
        service.delete(userId, id);
    }
}

