package com.syndicg5.controller;

import com.syndicg5.model.Appartement;
import com.syndicg5.service.impl.AppartementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppartementController {

    @Autowired
    AppartementServiceImpl appartementService;

    @PostMapping("/appartements")
    public void createAppartement(@RequestBody Appartement appartement) {
        appartementService.save(appartement);
    }

    @PutMapping("/appartements")
    public void updateAppartement(@RequestBody Appartement appartement) {
        appartementService.update(appartement);
    }

    @GetMapping("/appartements")
    public List<Appartement> getAllAppartements() {
        return appartementService.findAll();
    }

    @GetMapping("/appartements/{id}")
    public Appartement getAppartement(@PathVariable Long id) {
        return appartementService.findOne(id);
    }

    @DeleteMapping("/appartements/{id}")
    public void deleteAppartement(@PathVariable long id) {
        appartementService.delete(id);
    }
}

