package com.syndicg5.service;

import com.syndicg5.model.Immeuble;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public interface ImmeubleService {
    void save(Immeuble immeuble);

    void update(long id, Immeuble immeuble);

    List<Immeuble> findAll();

    List<Immeuble> findAllBySyndic(long id);

    List<Immeuble> findAllByNom(long syndicId, String nom);

    List<Immeuble> findAllByResidentEmail(String email);

    Immeuble findOne(Long id);

    void delete(Long id);

    Integer nomreImmeuble();

    List<Immeuble> findAllByResident(long id);

    SortedSet<Integer> findImmeubleDate(long id);

    Map<String, Map<Integer, Double>> findDepenseImmeuble(long id, int year);
}
