package com.syndicg5.service;

import com.syndicg5.model.Appartement;

import java.util.List;

public interface AppartementService {
    List<Appartement> save(Appartement appartement);
    List<Appartement> update(long id, Appartement appartement);
    void updateAppartementResident(long residentId, long[] appartementId);
    List<Appartement> findAllByImmeubleResident(long id, long residentId);
    List<Appartement> findAll();
    List<Appartement> findAllBySyndic(long id);
    List<Appartement> findAllByResident(long id);
    List<Appartement> findAllByImmeuble(long id);
    List<Appartement> findAppartementByImmeuble(long id);
    Appartement findOne(Long id);
    List<Appartement> delete(Long id);
}
