package com.syndicg5.service;

import com.syndicg5.model.Appartement;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public interface AppartementService {
    void save(Appartement appartement);
    void update(long id, Appartement appartement);
    void updatePartial(long id, Appartement appartement);
    void updateAppartementResident(long residentId, long appartementId, Appartement appartement);
    void updateAppartementsResident(long residentId, long[] appartementId);
    void deleteAppartementResident(long appartementId);
    List<Appartement> findAllByImmeubleResident(long id, long residentId);
    List<Appartement> findAll();
    List<Appartement> findAllBySyndic(long id);
    List<Appartement> findAllByResident(long id);
    List<Appartement> findAllByImmeuble(long id);
    List<Appartement> findAllEmptyByImmeuble(long id);
    List<Appartement> findAppartementByImmeuble(long id);
    List<Appartement> getAppartementByImmeuble(Long id);
    Appartement findOne(Long id);
    void delete(Long id);
    SortedSet<Integer> findAppartementDates(long id);
    Map<Integer, Map<Integer, Double>> findRevenusAppartement(long id, int year);
}
