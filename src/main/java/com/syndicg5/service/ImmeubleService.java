package com.syndicg5.service;

import com.syndicg5.model.Immeuble;
import com.syndicg5.model.Revenu;

import java.util.*;

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

    SortedSet<Integer> findImmeubleDates(long id);

    Map<Integer, Map<Integer, Double>> findRevenusImmeuble(long id, int year);

    Map<String, Map<Integer, Double>> findDepensesImmeuble(long id, int year);

    List<Immeuble> findAllByResident(long id);
}
