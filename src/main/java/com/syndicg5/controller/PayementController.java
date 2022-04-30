package com.syndicg5.controller;

import com.syndicg5.model.Payement;
import com.syndicg5.service.impl.PayementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PayementController {

    @Autowired
    PayementServiceImpl payementService;

    @PostMapping("/payements")
    public void createPayement(@RequestBody Payement payement) {
        payementService.save(payement);
    }

    @PutMapping("/payements")
    public void updatePayement(@RequestBody Payement payement) {
        payementService.update(payement);
    }

    @GetMapping("/payements")
    public List<Payement> getAllPayements() {
        return payementService.findAll();
    }

    @GetMapping("/payements/{id}")
    public Payement getPayement(@PathVariable Long id) {
        return payementService.findOne(id);
    }

    @DeleteMapping("/payements/{id}")
    public void deletePayement(@PathVariable long id) {
        payementService.delete(id);
    }
}

