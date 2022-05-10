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
    public List<Immeuble> createImmeuble(@RequestBody Immeuble immeuble) {
        return immeubleService.save(immeuble);
    }

    @PutMapping("/immeubles/{id}")
    public List<Immeuble> updateImmeuble(@PathVariable(value = "id") long id, @Valid @RequestBody Immeuble immeuble) {
        return immeubleService.update(id, immeuble);
    }

    @GetMapping("/immeubles")
    public List<Immeuble> getAllImmeubles() {
        return immeubleService.findAll();
    }

    @GetMapping("/immeubles/syndic/{id}")
    public List<Immeuble> getAllImmeublesBySyndic(@PathVariable(value = "id") long id) {
        return immeubleService.findAllBySyndic(id);
    }

    @GetMapping("/immeubles/{id}")
    public Immeuble getImmeuble(@PathVariable Long id) {
        return immeubleService.findOne(id);
    }

    @DeleteMapping("/immeubles/{id}")
    public List<Immeuble> deleteImmeuble(@PathVariable long id) {
        return immeubleService.delete(id);
    }
}
