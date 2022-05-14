package com.syndicg5.service.impl;

import com.syndicg5.model.Categorie;
import com.syndicg5.repository.CategorieRepository;
import com.syndicg5.service.CategorieService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategorieServiceImpl implements CategorieService {

    @Autowired
    CategorieRepository categorieRepository;

    @Override
    public List<Categorie> save(Categorie categorie) {
        long syndicId = categorie.getSyndic().getId();
        categorieRepository.save(categorie);
        return categorieRepository.findAllBySyndic(syndicId);
    }

    @Override
    public List<Categorie> update(long id, Categorie categorie) {
        long syndicId = categorieRepository.findById(id).get().getSyndic().getId();
        Categorie c = categorieRepository.findById(id).get();
        c.setLibelle(categorie.getLibelle());
        categorieRepository.save(c);
        return categorieRepository.findAllBySyndic(syndicId);
    }

    @Override
    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    @Override
    public List<Categorie> findAllBySyndic(long id) {
        return categorieRepository.findAllBySyndic(id);
    }

    @Override
    public Categorie findOne(Long id) {
        return categorieRepository.findById(id).get();
    }

    @Override
    public List<Categorie> delete(Long id) {
        long syndicId = categorieRepository.findById(id).get().getSyndic().getId();
        categorieRepository.deleteById(id);
        return categorieRepository.findAllBySyndic(syndicId);
    }
}
