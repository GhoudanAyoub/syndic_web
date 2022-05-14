package com.syndicg5.service;

import com.syndicg5.model.Categorie;
import com.syndicg5.model.Resident;

import java.util.List;

public interface CategorieService {
    List<Categorie> save(Categorie categorie);
    List<Categorie> update(long id, Categorie categorie);
    List<Categorie> findAll();
    List<Categorie> findAllBySyndic(long id);
    Categorie findOne(Long id);
    List<Categorie> delete(Long id);
}
