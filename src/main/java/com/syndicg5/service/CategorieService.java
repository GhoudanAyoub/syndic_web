package com.syndicg5.service;

import com.syndicg5.model.Categorie;
import com.syndicg5.model.Resident;

import java.util.List;

public interface CategorieService {
    void save(Categorie categorie);
    void update(long id, Categorie categorie);
    List<Categorie> findAll();
    List<Categorie> findAllBySyndic(long id);
    List<Categorie> findAllByLibelle(long syndicId, String libelle);
    Categorie findOne(Long id);
    void delete(Long id);
}
