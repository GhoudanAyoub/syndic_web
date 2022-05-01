package com.syndicg5.controller;

import com.syndicg5.model.Immeuble;
import com.syndicg5.service.impl.ImmeubleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/immeubles/{id}")
    public Immeuble getImmeuble(@PathVariable Long id) {
        return immeubleService.findOne(id);
    }

    @DeleteMapping("/immeubles/{id}")
    public void deleteImmeuble(@PathVariable long id) {
        immeubleService.delete(id);
    }
}
