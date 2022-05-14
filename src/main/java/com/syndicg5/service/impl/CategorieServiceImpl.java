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
    public void save(Categorie categorie) {
        categorieRepository.save(categorie);
    }

    @Override
    public void update(long id, Categorie categorie) {
        Categorie c = categorieRepository.findById(id).get();
        c.setLibelle(categorie.getLibelle());
        categorieRepository.save(c);
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
    public List<Categorie> findAllByLibelle(long syndicId, String libelle) {
        return categorieRepository.findAllByLibelle(syndicId, libelle);
    }

    @Override
    public Categorie findOne(Long id) {
        return categorieRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        categorieRepository.deleteById(id);
    }
}
