package com.syndicg5.service;

import com.syndicg5.model.Immeuble;

import java.util.List;

public interface ImmeubleService {
    void save(Immeuble immeuble);
    void update(long id, Immeuble immeuble);
    List<Immeuble> findAll();
    List<Immeuble> findAllBySyndic(long id);
    Immeuble findOne(Long id);
    void delete(Long id);
}

