package com.syndicg5.service;

import com.syndicg5.model.Appartement;

import java.util.List;

public interface AppartementService {
    void save(Appartement appartement);
    void update(long id, Appartement appartement);
    List<Appartement> findAll();
    List<Appartement> getAppartementByImmeuble(Long id);
    Appartement findOne(Long id);
    void delete(Long id);
}
