package com.syndicg5.service;

import com.syndicg5.model.Payement;

import java.util.List;

public interface PayementService {
    void save(Payement payement);
    void update(Payement payement);
    List<Payement> findAll();
    Payement findOne(Long id);
    void delete(Long id);
}

