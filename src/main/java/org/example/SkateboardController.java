package org.example;

import org.example.entities.Skateboard;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("api")
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
}
