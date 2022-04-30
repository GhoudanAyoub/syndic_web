package com.syndicg5.controller;

import com.syndicg5.model.Categorie;
import com.syndicg5.service.impl.CategorieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategorieController {

    @Autowired
    CategorieServiceImpl categorieService;

    @PostMapping("/categories")
    public void createCategorie(@RequestBody Categorie categorie) {
        categorieService.save(categorie);
    }

    @PutMapping("/categories")
    public void updateCategorie(@RequestBody Categorie categorie) {
        categorieService.update(categorie);
    }

    @GetMapping("/categories")
    public List<Categorie> getAllCategories() {
        return categorieService.findAll();
    }

    @GetMapping("/categories/{id}")
    public Categorie getCategorie(@PathVariable Long id) {
        return categorieService.findOne(id);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategorie(@PathVariable long id) {
        categorieService.delete(id);
    }
}


