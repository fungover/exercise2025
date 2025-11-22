package org.example;

import org.example.entities.Skateboard;
import org.example.entities.TruckBrand;
import org.example.entities.TruckSize;
import org.example.repositories.SkateboardRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class SkateboardController {

    private final SkateboardRepository repository;

    public SkateboardController(SkateboardRepository repository) {
        this.repository = repository;
    }

    @GetMapping("skateboards")
    public List<Object> findAllBoards() {
        return repository.findBoardBy();
    }

    @GetMapping("skateboards/id/{id}")
    public Skateboard findBoardById(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @GetMapping("skateboards/brand/{brand}")
    public List<Skateboard> findBoardBy(@PathVariable String brand) {
        return repository.findBoardBy(brand);
    }

    @GetMapping("/trucks/{boardWidth}")
    public Map<TruckBrand, TruckSize> getFittingTrucks(@PathVariable double boardWidth) {
        return repository.findByBoardWidth(boardWidth).stream()
                .collect(Collectors.toMap(TruckSize::getBrandId, Function.identity()));
    }

}
