package com.syndicg5.service;

import com.syndicg5.model.Depense;

import java.util.List;

public interface DepenseService {
    void save(Depense depense);
    void update(long id, Depense depense);
    List<Depense> findAll();
    Depense findOne(Long id);
    void delete(Long id);
}
