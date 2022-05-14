package com.syndicg5.service;

import com.syndicg5.model.Revenu;

import java.util.List;

public interface RevenuService {
    List<Revenu> save(Revenu revenu);
    List<Revenu> update(long id, Revenu revenu);
    List<Revenu> findAll();
    List<Revenu> findRevenusByImmeuble(long id);
    List<Revenu> findRevenusByAppartement(long id);
    List<Revenu> findRevenusBySyndic(long id);
    Revenu findOne(Long id);
    List<Revenu> delete(Long id);
}

