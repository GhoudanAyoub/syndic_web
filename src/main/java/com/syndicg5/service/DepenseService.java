package com.syndicg5.service;

import com.syndicg5.model.Depense;
import com.syndicg5.model.Revenu;

import java.util.List;

public interface DepenseService {
    List<Depense> save(Depense depense);
    List<Depense> update(long id, Depense depense);
    List<Depense> findAll();
    List<Depense> findDepensesByImmeuble(long id);
    List<Depense> findDepensesBySyndic(long id);
    Depense findOne(Long id);
    List<Depense> delete(Long id);
}
