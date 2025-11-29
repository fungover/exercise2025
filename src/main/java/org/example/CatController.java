package org.example;

import jakarta.validation.Valid;
import org.example.entities.Food;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api")
public class CatController {

    private final CatRepository repository;

    public CatController(CatRepository repository) {
        this.repository = repository;
    }

    @GetMapping("cats")
    @PreAuthorize("hasRole('API')")  //Spring Expression Language (SpEL)
    public List<Cat> getAll() {
        return repository.findCatsBy().stream()
                .map(cat -> new Cat(cat.getName(), cat.getAge(), cat.getFavorites()))
                .toList();
    }

    @GetMapping("cats/{name}")
    @PreAuthorize("hasRole('API')")
    public Cat getByName(@PathVariable String name) {
        return repository.findBy(name)
                .map(cat -> new Cat(cat.getName(), cat.getAge(), cat.getFavorites()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cat not found: " + name));
    }

    @PostMapping("cats")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('API')")
    public Cat createCat(@Valid @RequestBody Cat cat) {
        List<Food> favorites = cat.foodList() != null ? cat.foodList() : List.of();
        var entity = new org.example.entities.Cat(cat.name(), cat.age(), favorites);
        var saved = repository.save(entity);
        return new Cat(saved.getName(), saved.getAge(), saved.getFavorites());
    }
}
