package com.syndicg5.service;

import com.syndicg5.model.Depense;
import com.syndicg5.model.Revenu;

import java.util.List;

public interface DepenseService {
    void save(Depense depense);

    void update(long id, Depense depense);

    List<Depense> findAll();

    List<Depense> findDepensesByImmeuble(long id);

    List<Depense> findDepensesBySyndic(long id);

    Depense findOne(Long id);

    void delete(Long id);

    double finddepenseMax();

    List<Object[]> depenseParAnnee();

    List<Object[]> depenseParMontant();
}
