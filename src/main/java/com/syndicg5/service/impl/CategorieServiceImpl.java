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
    public void update(Categorie categorie) {
        Categorie c = categorieRepository.getById(categorie.getId());
        if (c != null)
            categorieRepository.save(c);
    }

    @Override
    public List<Categorie> findAll() {
        return categorieRepository.findAll();
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
