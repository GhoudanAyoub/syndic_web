package com.syndicg5.controller;

import com.syndicg5.model.Revenu;
import com.syndicg5.service.impl.RevenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RevenuController {

    @Autowired
    RevenuServiceImpl revenuService;

    @PostMapping("/revenus")
    public void createRevenu(@RequestBody Revenu revenu) {
        revenuService.save(revenu);
    }

    @PutMapping("/revenus")
    public void updateRevenu(@RequestBody Revenu revenu) {
        revenuService.update(revenu);
    }

    @GetMapping("/revenus")
    public List<Revenu> getAllRevenus() {
        return revenuService.findAll();
    }

    @GetMapping("/revenus/{id}")
    public Revenu getRevenu(@PathVariable Long id) {
        return revenuService.findOne(id);
    }

    @DeleteMapping("/revenus/{id}")
    public void deleteRevenu(@PathVariable long id) {
        revenuService.delete(id);
    }
}



