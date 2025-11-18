package org.example.controller;

import org.example.model.Location;
import org.example.repository.LocationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationRepository locationRepo;

    public LocationController(LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    @GetMapping
    public List<Location> getAllLocations() {
        return locationRepo.findAll();
    }

    @PostMapping
    public Location createLocation(@RequestBody Location location) {
        return locationRepo.save(location);
    }
}
