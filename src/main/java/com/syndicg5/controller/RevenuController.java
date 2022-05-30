package com.syndicg5.controller;

import com.syndicg5.model.Revenu;
import com.syndicg5.service.impl.RevenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class RevenuController {

    @Autowired
    RevenuServiceImpl revenuService;

    @PostMapping("/revenus")
    public void createRevenu(@RequestBody Revenu revenu) {
        revenuService.save(revenu);
    }

    @PutMapping("/revenus/{id}")
    public void updateRevenu(@PathVariable(value = "id") long id, @Valid @RequestBody Revenu revenu) {
        revenuService.update(id, revenu);
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
    public void deleteRevenu(@PathVariable long id) {
        revenuService.delete(id);
    }

    @GetMapping("/revenus/max")
    public double findRevenueMax() {
        return revenuService.findRevenueMax();
    }

    @GetMapping("/revenus/annee")
    public List<Object[]> revenuParAnnee() {
        return revenuService.revenuParAnnee();
    }

    @GetMapping("/revenus/montant")
    public List<Object[]> revenuParMontant() {
        return revenuService.revenuParMontant();
    }

    //Todo dial lghoudan
    @GetMapping("/revenusByResident/{id}")
    public List<Revenu> findRevenusByResident(@PathVariable long id) {
        return revenuService.findRevenusByResident(id);
    }

    @GetMapping("/revenus/dates/{id}")
    public List<Integer> revenuDate(@PathVariable long id) {
        return revenuService.findRevenusDate(id);
    }

    @GetMapping("/revenus/{id}/{year}")
    public Map<Integer, Map<Integer, Double>> findRevenusAppartement(@PathVariable long id, @PathVariable int year) {
        return revenuService.findRevenusAppartement(id, year);
    }

}
