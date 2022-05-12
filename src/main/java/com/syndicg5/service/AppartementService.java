package com.syndicg5.service;

import com.syndicg5.model.Appartement;

import java.util.List;

public interface AppartementService {
    List<Appartement> save(Appartement appartement);
    List<Appartement> update(long id, Appartement appartement);
    List<Appartement> findAll();
    List<Appartement> findAllBySyndic(long id);
    List<Appartement> getAppartementByImmeuble(Long id);
    Appartement findOne(Long id);
    List<Appartement> delete(Long id);
}
