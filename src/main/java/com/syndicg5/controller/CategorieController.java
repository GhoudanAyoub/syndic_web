package com.syndicg5.controller;

import com.syndicg5.model.Categorie;
import com.syndicg5.service.impl.CategorieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategorieController {

    @Autowired
    CategorieServiceImpl categorieService;

    @PostMapping("/categories")
    public List<Categorie> createCategorie(@RequestBody Categorie categorie) {
        return categorieService.save(categorie);
    }

    @PutMapping("/categories/{id}")
    public List<Categorie> updateCategorie(@PathVariable(value = "id") long id, @Valid @RequestBody Categorie categorie) {
        return categorieService.update(id, categorie);
    }

    @GetMapping("/categories")
    public List<Categorie> getAllCategories() {
        return categorieService.findAll();
    }

    @GetMapping("/categories/syndic/{syndicId}")
    public List<Categorie> getAllCategoriesBySyndic(@PathVariable(value = "syndicId") long syndicId) {
        return categorieService.findAllBySyndic(syndicId);
    }

    @GetMapping("/categories/{id}")
    public Categorie getCategorie(@PathVariable Long id) {
        return categorieService.findOne(id);
    }

    @DeleteMapping("/categories/{id}")
    public List<Categorie> deleteCategorie(@PathVariable long id) {
        return categorieService.delete(id);
    }
}


