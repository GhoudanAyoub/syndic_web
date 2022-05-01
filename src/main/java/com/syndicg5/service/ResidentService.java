package com.syndicg5.service;

import com.syndicg5.model.Resident;

import java.util.List;

public interface ResidentService {
    void save(Resident resident);
    void update(long id, Resident resident);
    List<Resident> findAll();
    Resident findOne(Long id);
    void delete(Long id);
}

