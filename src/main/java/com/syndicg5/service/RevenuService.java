package com.syndicg5.service;

import com.syndicg5.model.Revenu;

import java.util.List;

public interface RevenuService {
    void save(Revenu revenu);
    void update(Revenu revenu);
    List<Revenu> findAll();
    Revenu findOne(Long id);
    void delete(Long id);
}

