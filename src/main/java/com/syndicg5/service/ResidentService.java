package com.syndicg5.service;

import com.syndicg5.model.Resident;
import com.syndicg5.model.Syndic;

import java.util.List;

public interface ResidentService {
    Resident save(Resident resident);

    Resident update(long id, Resident resident);

    List<Resident> findAll();

    List<Resident> findAllBySyndic(long id);

    List<Resident> findAllByNom(long syndicId, String nom);

    Resident findOne(Long id);

    Resident findOneByEmail(String email);

    void delete(long id);

    Integer nombreResident();

    Resident checklogin(String email,String mdp);

    void updatePassword(long id, Resident resident);
}
