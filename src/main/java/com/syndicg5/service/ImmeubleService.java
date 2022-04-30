package com.syndicg5.service;

import com.syndicg5.model.Immeuble;

import java.util.List;

public interface ImmeubleService {
    void save(Immeuble immeuble);
    void update(Immeuble immeuble);
    List<Immeuble> findAll();
    Immeuble findOne(Long id);
    void delete(Long id);
}

