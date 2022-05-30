package com.syndicg5.controller;

import com.syndicg5.model.Immeuble;
import com.syndicg5.model.Revenu;
import com.syndicg5.service.impl.ImmeubleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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

    @GetMapping("/immeubles/resident/{email}")
    public List<Immeuble> getAllImmeublesByResident(@PathVariable(value = "email") String email) {
        return immeubleService.findAllByResidentEmail(email);
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

    @GetMapping("/immeubles/dates/{id}")
    public SortedSet<Integer> findImmeubleDates(@PathVariable long id) {
        return immeubleService.findImmeubleDates(id);
    }

    @GetMapping("/immeubles/revenus/{id}/{year}")
    public Map<Integer, Map<Integer, Double>> findRevenusImmeuble(@PathVariable long id, @PathVariable int year) {
        return immeubleService.findRevenusImmeuble(id, year);
    }

    @GetMapping("/immeubles/depenses/{id}/{year}")
    public Map<String, Map<Integer, Double>> findDepensesImmeuble(@PathVariable long id, @PathVariable int year) {
        return immeubleService.findDepensesImmeuble(id, year);
    }

    @GetMapping("/immeubles/residentid/{id}")
    public List<Immeuble> getAllImmeublesByResident(@PathVariable(value = "id") long id) {
        return immeubleService.findAllByResident(id);
    }

}
