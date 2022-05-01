package com.syndicg5.controller;

import com.syndicg5.model.Resident;
import com.syndicg5.service.impl.ResidentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ResidentController {

    @Autowired
    ResidentServiceImpl residentService;

    @PostMapping("/residents")
    public void createResident(@RequestBody Resident resident) {
        residentService.save(resident);
    }

    @PutMapping("/residents/{id}")
    public void updateResident(@PathVariable(value = "id") long id, @Valid @RequestBody Resident resident) {
        residentService.update(id, resident);
    }

    @GetMapping("/residents")
    public List<Resident> getAllResidents() {
        return residentService.findAll();
    }

    @GetMapping("/residents/{id}")
    public Resident getResident(@PathVariable Long id) {
        return residentService.findOne(id);
    }

    @DeleteMapping("/residents/{id}")
    public void deleteResident(@PathVariable long id) {
        residentService.delete(id);
    }
}


