package com.syndicg5.controller;

import com.syndicg5.model.Revenu;
import com.syndicg5.service.impl.RevenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RevenuController {

    @Autowired
    RevenuServiceImpl revenuService;

    @PostMapping("/revenus")
    public List<Revenu> createRevenu(@RequestBody Revenu revenu) {
        return revenuService.save(revenu);
    }

    @PutMapping("/revenus/{id}")
    public List<Revenu> updateRevenu(@PathVariable(value = "id") long id, @Valid @RequestBody Revenu revenu) {
        return revenuService.update(id, revenu);
    }

    @GetMapping("/revenus")
    public List<Revenu> getAllRevenus() {
        return revenuService.findAll();
    }

    @GetMapping("/revenus/{id}")
    public Revenu getRevenu(@PathVariable Long id) {
        return revenuService.findOne(id);
    }

    @GetMapping("/revenusByImmeuble/{id}")
    public List<Revenu> findRevenusByImmeuble(@PathVariable long id) {
        return revenuService.findRevenusByImmeuble(id);
    }

    @GetMapping("/revenusByAppartement/{id}")
    public List<Revenu> findRevenusByAppartement(@PathVariable long id) {
        return revenuService.findRevenusByAppartement(id);
    }

    @GetMapping("/revenus/syndic/{id}")
    public List<Revenu> findRevenusBySyndic(@PathVariable long id) {
        return revenuService.findRevenusBySyndic(id);
    }


    @DeleteMapping("/revenus/{id}")
    public List<Revenu> deleteRevenu(@PathVariable long id) {
        return revenuService.delete(id);
    }
}



