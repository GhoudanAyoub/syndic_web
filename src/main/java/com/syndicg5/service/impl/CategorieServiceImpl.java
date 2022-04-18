package com.syndicg5.service.impl;

import com.syndicg5.domain.Categorie;
import com.syndicg5.repository.CategorieRepository;
import com.syndicg5.service.CategorieService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Categorie}.
 */
@Service
public class CategorieServiceImpl implements CategorieService {

    private final Logger log = LoggerFactory.getLogger(CategorieServiceImpl.class);

    private final CategorieRepository categorieRepository;

    public CategorieServiceImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Override
    public Categorie save(Categorie categorie) {
        log.debug("Request to save Categorie : {}", categorie);
        return categorieRepository.save(categorie);
    }

    @Override
    public Categorie update(Categorie categorie) {
        log.debug("Request to save Categorie : {}", categorie);
        return categorieRepository.save(categorie);
    }

    @Override
    public Optional<Categorie> partialUpdate(Categorie categorie) {
        log.debug("Request to partially update Categorie : {}", categorie);

        return categorieRepository
            .findById(categorie.getId())
            .map(existingCategorie -> {
                if (categorie.getLibelle() != null) {
                    existingCategorie.setLibelle(categorie.getLibelle());
                }

                return existingCategorie;
            })
            .map(categorieRepository::save);
    }

    @Override
    public List<Categorie> findAll() {
        log.debug("Request to get all Categories");
        return categorieRepository.findAll();
    }

    @Override
    public Optional<Categorie> findOne(String id) {
        log.debug("Request to get Categorie : {}", id);
        return categorieRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Categorie : {}", id);
        categorieRepository.deleteById(id);
    }
}
