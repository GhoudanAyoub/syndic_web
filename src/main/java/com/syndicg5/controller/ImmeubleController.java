package com.syndicg5.controller;

import com.syndicg5.model.Immeuble;
import com.syndicg5.service.impl.ImmeubleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class ImmeubleController {

    @Autowired
    ImmeubleServiceImpl immeubleService;

    @PostMapping("/immeubles")
    public void createImmeuble(@RequestBody Immeuble immeuble) {
        immeubleService.save(immeuble);
    }

    @PutMapping("/immeubles/{id}")
    public void updateImmeuble(@PathVariable(value = "id") long id, @Valid @RequestBody Immeuble immeuble) {
        immeubleService.update(id, immeuble);
    }

    @GetMapping("/immeubles")
    public List<Immeuble> getAllImmeubles() {
        return immeubleService.findAll();
    }

    @GetMapping("/immeubles/syndic/{id}")
    public List<Immeuble> getAllImmeublesBySyndic(@PathVariable(value = "id") long id) {
        return immeubleService.findAllBySyndic(id);
    }

    @GetMapping("/immeubles/syndic/nom/{syndicId}/{nom}")
    public List<Immeuble> getAllImmeublesByNom(@PathVariable(value = "syndicId") long syndicId,
            @PathVariable(value = "nom") String nom) {
        return immeubleService.findAllByNom(syndicId, nom);
    }

    @GetMapping("/immeubles/{id}")
    public Immeuble getImmeuble(@PathVariable Long id) {
        return immeubleService.findOne(id);
    }

    @DeleteMapping("/immeubles/{id}")
    public void deleteImmeuble(@PathVariable long id) {
        immeubleService.delete(id);
    }

    @GetMapping("/immeubles/nbr")
    public Integer nomreImmeuble() {
        return immeubleService.nomreImmeuble();
    }

}
