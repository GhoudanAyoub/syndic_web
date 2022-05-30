package com.syndicg5.service;

import com.syndicg5.model.Revenu;

import java.util.List;

public interface RevenuService {
    void save(Revenu revenu);

    void update(long id, Revenu revenu);

    List<Revenu> findAll();

    List<Revenu> findRevenusByImmeuble(long id);

    List<Revenu> findRevenusByAppartement(long id);

    List<Revenu> findRevenusBySyndic(long id);

    Revenu findOne(Long id);

    double findRevenueMax();

    void delete(Long id);

    List<Object[]> revenuParAnnee();

    List<Object[]> revenuParMontant();

    List<Revenu> findRevenusByResident(long id);

}
