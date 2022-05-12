package com.syndicg5.controller;

import com.syndicg5.model.Appartement;
import com.syndicg5.service.impl.AppartementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppartementController {

    @Autowired
    AppartementServiceImpl appartementService;

    @PostMapping("/appartements")
    public List<Appartement> createAppartement(@RequestBody Appartement appartement) {
        return appartementService.save(appartement);
    }

    @PutMapping("/appartements/{id}")
    public List<Appartement> updateAppartement(@PathVariable(value = "id") long id, @Valid @RequestBody Appartement appartement) {
        return appartementService.update(id, appartement);
    }

    @GetMapping("/appartements")
    public List<Appartement> getAllAppartements() {
        return appartementService.findAll();
    }

    @GetMapping("/appartements/syndic/{id}")
    public List<Appartement> getAllAppartementsBySyndic(@PathVariable(value = "id") long id) {
        return appartementService.findAllBySyndic(id);
    }

    @GetMapping("/appartements/{id}")
    public Appartement getAppartement(@PathVariable Long id) {
        return appartementService.findOne(id);
    }

    @GetMapping("/appartementByImmeuble/{id}")
    public List<Appartement> getAppartementByImmeuble(@PathVariable Long id) {
        return appartementService.getAppartementByImmeuble(id);
    }

    @DeleteMapping("/appartements/{id}")
    public List<Appartement> deleteAppartement(@PathVariable long id) {
        return appartementService.delete(id);
    }
}

