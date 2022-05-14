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

    @PutMapping("/appartements/partial/{id}")
    public void updatePartialAppartement(@PathVariable(value = "id") long id, @Valid @RequestBody Appartement appartement) {
        appartementService.updatePartial(id, appartement);
    }

    @PutMapping("/appartements/resident/{residentId}/{appartementId}")
    public void updateAppartementResident(@PathVariable(value = "residentId") long residentId, @PathVariable(value = "appartementId") long appartementId, @Valid @RequestBody Appartement appartement) {
        appartementService.updateAppartementResident(residentId, appartementId, appartement);
    }

    @PutMapping("/appartements/resident/{residentId}")
    public void updateAppartementsResident(@PathVariable(value = "residentId") long residentId, @RequestParam(value = "appartementId[]") long[] appartementId) {
        appartementService.updateAppartementsResident(residentId, appartementId);
    }

    @PutMapping("/appartements/del/resident/{appartementId}")
    public void deleteAppartementResident(@PathVariable(value = "appartementId") long appartementId) {
        appartementService.deleteAppartementResident(appartementId);
    }

    @GetMapping("/appartementByImmeuble/{id}")
    public List<Appartement> getAppartementByImmeuble(@PathVariable Long id) {
        return appartementService.getAppartementByImmeuble(id);
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

    @GetMapping("/appartements/resident/{id}")
    public List<Appartement> getAllByResident(@PathVariable long id) {
        return appartementService.findAllByResident(id);
    }

    @GetMapping("/appartements/syndic/immeuble/{id}")
    public List<Appartement> getAllByImmeuble(@PathVariable long id) {
        return appartementService.findAllByImmeuble(id);
    }

    @GetMapping("/appartements/empty/immeuble/{id}")
    public List<Appartement> getAllEmptyByImmeuble(@PathVariable long id) {
        return appartementService.findAllEmptyByImmeuble(id);
    }

    @GetMapping("/appartements/syndic/resident/immeuble/{syndicId}/{residentId}")
    public List<Appartement> getAllByImmeubleResident(@PathVariable(value = "syndicId") long syndicId, @PathVariable(value = "residentId") long residentId) {
        return appartementService.findAllByImmeubleResident(syndicId, residentId);
    }

    @GetMapping("/appartements/immeuble/{id}")
    public List<Appartement> getAppartementByImmeuble(@PathVariable long id) {
        return appartementService.findAppartementByImmeuble(id);
    }

    @DeleteMapping("/appartements/{id}")
    public List<Appartement> deleteAppartement(@PathVariable long id) {
        return appartementService.delete(id);
    }
}

