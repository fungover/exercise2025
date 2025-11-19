package org.example.controller;


import org.example.dto.StoreDto;
import org.example.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StoreController{

    private final StoreRepository storeRepository;

    @Autowired
    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @GetMapping("/stores")
    public List<StoreDto> getStores() {
        return storeRepository.findAll().stream()
                .map(store -> new StoreDto(store.getStoreName())).toList();
    }

    @GetMapping("/stores/author/{author}")
    public List<StoreDto> getBooksByAuthor(@PathVariable("author") String author) {
        return storeRepository.findStoreByAuthorName(author).stream()
                .map(StoreDto::new).toList();
    }

    @GetMapping("/stores/{name}")
    public List<StoreDto> getBooksByName(@PathVariable("name") String name) {
        return storeRepository.findByStoreName(name).stream().map(store -> new StoreDto(store.getStoreName())).toList();
    }

}
