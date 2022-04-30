package com.syndicg5.controller;

import com.syndicg5.model.Syndic;
import com.syndicg5.service.impl.SyndicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SyndicController {

    @Autowired
    SyndicServiceImpl syndicService;

    @PostMapping("/syndics")
    public void createSyndic(@RequestBody Syndic syndic) {
        syndicService.save(syndic);
    }

    @PutMapping("/syndics")
    public void updateSyndic(@RequestBody Syndic syndic) {
        syndicService.update(syndic);
    }

    @GetMapping("/syndics")
    public List<Syndic> getAllSyndics() {
        return syndicService.findAll();
    }

    @GetMapping("/syndics/{id}")
    public Syndic getSyndic(@PathVariable Long id) {
        return syndicService.findOne(id);
    }

    @DeleteMapping("/syndics/{id}")
    public void deleteSyndic(@PathVariable long id) {
        syndicService.delete(id);
    }
}




