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
    public void createCategorie(@RequestBody Categorie categorie) {
        categorieService.save(categorie);
    }

    @PutMapping("/categories/{id}")
    public void updateCategorie(@PathVariable(value = "id") long id, @Valid @RequestBody Categorie categorie) {
        categorieService.update(id, categorie);
    }

    @GetMapping("/categories")
    public List<Categorie> getAllCategories() {
        return categorieService.findAll();
    }

    @GetMapping("/categories/syndic/{syndicId}")
    public List<Categorie> getAllCategoriesBySyndic(@PathVariable(value = "syndicId") long syndicId) {
        return categorieService.findAllBySyndic(syndicId);
    }

    @GetMapping("/categories/syndic/libelle/{syndicId}/{libelle}")
    public List<Categorie> getAllCategoriesByLibelle(@PathVariable(value = "syndicId") long syndicId, @PathVariable(value = "libelle") String libelle) {
        return categorieService.findAllByLibelle(syndicId, libelle);
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


