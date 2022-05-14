package com.syndicg5.service;

import com.syndicg5.model.Resident;

import java.util.List;

public interface ResidentService {
    Resident save(Resident resident);
    Resident update(long id, Resident resident);
    List<Resident> findAll();
    List<Resident> findAllBySyndic(long id);
    Resident findOne(Long id);
    List<Resident> delete(long id);
}

