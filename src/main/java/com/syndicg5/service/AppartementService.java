package com.syndicg5.service;

import com.syndicg5.model.Appartement;

import java.util.List;

public interface AppartementService {
    void save(Appartement appartement);
    void update(Appartement appartement);
    List<Appartement> findAll();
    Appartement findOne(Long id);
    void delete(Long id);
}
