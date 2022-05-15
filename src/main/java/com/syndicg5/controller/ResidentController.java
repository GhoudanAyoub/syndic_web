package com.syndicg5.controller;

import com.syndicg5.model.Resident;
import com.syndicg5.service.impl.ResidentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class ResidentController {

    @Autowired
    ResidentServiceImpl residentService;

    @PostMapping("/residents")
    public Resident createResident(@RequestBody Resident resident) {
        return residentService.save(resident);
    }

    @PutMapping("/residents/{id}")
    public Resident updateResident(@PathVariable(value = "id") long id, @Valid @RequestBody Resident resident) {
        return residentService.update(id, resident);
    }

    @GetMapping("/residents")
    public List<Resident> getAllResidents() {
        return residentService.findAll();
    }

    @GetMapping("/residents/syndic/{syndicId}")
    public List<Resident> getAllResidentsBySyndic(@PathVariable(value = "syndicId") long syndicId) {
        return residentService.findAllBySyndic(syndicId);
    }

    // Todo : Don't Touch this One
    @GetMapping("/residentByEmail/{email}")
    public Resident findOneByEmail(@PathVariable String email) {
        return residentService.findOneByEmail(email);
    }

    @GetMapping("/residents/syndic/nom/{syndicId}/{nom}")
    public List<Resident> getAllResidentsByNom(@PathVariable(value = "syndicId") long syndicId,
            @PathVariable(value = "nom") String nom) {
        return residentService.findAllByNom(syndicId, nom);
    }

    @GetMapping("/residents/{id}")
    public Resident getResident(@PathVariable Long id) {
        return residentService.findOne(id);
    }

    @DeleteMapping("/residents/{id}")
    public void deleteResident(@PathVariable long id) {
        residentService.delete(id);
    }

    @GetMapping("/residents/nbr")
    public Integer nombreResident() {
        return residentService.nombreResident();
    }

}
