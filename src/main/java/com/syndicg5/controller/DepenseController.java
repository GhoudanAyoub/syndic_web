package com.syndicg5.controller;

import com.syndicg5.model.Depense;
import com.syndicg5.model.Revenu;
import com.syndicg5.service.impl.DepenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class DepenseController {

    @Autowired
    DepenseServiceImpl depenseService;

    @PostMapping("/depenses")
    public void createDepense(@RequestBody Depense depense) {
        depenseService.save(depense);
    }

    @PutMapping("/depenses/{id}")
    public void updateDepense(@PathVariable(value = "id") long id, @Valid @RequestBody Depense depense) {
        depenseService.update(id, depense);
    }

    @GetMapping("/depenses")
    public List<Depense> getAllDepenses() {
        return depenseService.findAll();
    }

    @GetMapping("/depenses/{id}")
    public Depense getDepense(@PathVariable Long id) {
        return depenseService.findOne(id);
    }

    @GetMapping("/depenseByImmeuble/{id}")
    public List<Depense> findDepensesByImmeuble(@PathVariable long id) {
        return depenseService.findDepensesByImmeuble(id);
    }

    @GetMapping("/depenses/syndic/{syndicId}")
    public List<Depense> findDepensesBySyndic(@PathVariable(value = "syndicId") long syndicId) {
        return depenseService.findDepensesBySyndic(syndicId);
    }

    @DeleteMapping("/depenses/{id}")
    public void deleteDepense(@PathVariable long id) {
        depenseService.delete(id);
    }

    @GetMapping("/depenses/max")
    public double finddepenseMax() {
        return depenseService.finddepenseMax();
    }

    @GetMapping("/depenses/annee")
    public List<Object[]> depenseParAnnee() {
        return depenseService.depenseParAnnee();
    }

    @GetMapping("/depenses/montant")
    public List<Object[]> depenseParMontant() {
        return depenseService.depenseParMontant();
    }

}
