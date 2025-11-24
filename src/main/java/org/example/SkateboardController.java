package org.example;

import org.example.entities.DTOs.TruckDTO;
import org.example.entities.Deck;
import org.example.repositories.SkateboardRepository;
import org.example.repositories.TruckSizeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SkateboardController {

    private final SkateboardRepository repository;
    private final TruckSizeRepository truckSizeRepository;

    public SkateboardController(SkateboardRepository repository,
                                TruckSizeRepository truckSizeRepository) {
        this.repository = repository;
        this.truckSizeRepository = truckSizeRepository;
    }

    @GetMapping("decks")
    public List<Object> findAllDecks() {
        return repository.findDeckBy();
    }

    @GetMapping("decks/id/{id}")
    public Deck findDeckById(@PathVariable Integer id) {
        Deck deck = repository.findById(id).orElseThrow();
        deck.setFittingTrucks(truckSizeRepository.findTruckByWidth(deck.getBoardWidth()));
        return deck;
    }

    @GetMapping("decks/brand/{brand}")
    public List<Deck> findDeckBy(@PathVariable String brand) {
        List<Deck> decks = repository.findDeckBy(brand);
        for (Deck deck : decks) {
            deck.setFittingTrucks(truckSizeRepository.findTruckByWidth(deck.getBoardWidth()));
        }
        return decks;
    }

    @GetMapping("trucks/{boardWidth}")
    public List<TruckDTO> findFittingTrucks(@PathVariable double boardWidth) {
        return truckSizeRepository.findTruckByWidth(boardWidth);
    }

}
