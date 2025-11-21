package org.example;

import org.example.entities.Skateboard;
import org.example.repositories.SkateboardRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SkateboardController {

    private final SkateboardRepository repository;

    public SkateboardController(SkateboardRepository repository) {
        this.repository = repository;
    }

    @GetMapping("skateboards")
    public List<Skateboard> findAll() {
        return repository.findBoardBy().stream()
                .map(sb -> new Skateboard(sb.getBrand(), sb.getBoardWidth()))
                .toList();
    }

    @GetMapping("skateboards/{brand}")
    public List<Skateboard> findBoardByBrand(@PathVariable String brand) {
        return repository.findBoardBy(brand).stream()
                .map(sb -> new Skateboard(sb.getBrand(), sb.getBoardWidth()))
                .toList();
    }

    @GetMapping("skateboards/{id}")
    public Skateboard findBoardById(@PathVariable Integer id) {
        return repository.findBoardBy(id)
                .map(sb -> new Skateboard(sb.getBrand(), sb.getBoardWidth()))
                .orElseThrow();
    }

    @GetMapping("skateboards/{id}/trucks")
    public Skateboard findBoardTrucks(@PathVariable Integer id) {
        return repository.findBoardBy(id)
                .map(sb -> new Skateboard(sb.getBrand(), sb.getBoardWidth()))
                .orElseThrow();
    }
}
