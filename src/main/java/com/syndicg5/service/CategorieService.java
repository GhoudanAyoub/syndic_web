package com.syndicg5.service;

import com.syndicg5.model.Categorie;

import java.util.List;

public interface CategorieService {
    void save(Categorie categorie);
    void update(long id, Categorie categorie);
    List<Categorie> findAll();
    Categorie findOne(Long id);
    void delete(Long id);
}
