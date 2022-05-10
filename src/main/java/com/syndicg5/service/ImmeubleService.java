package com.syndicg5.service;

import com.syndicg5.model.Immeuble;

import java.util.List;

public interface ImmeubleService {
    List<Immeuble> save(Immeuble immeuble);
    List<Immeuble> update(long id, Immeuble immeuble);
    List<Immeuble> findAll();
    List<Immeuble> findAllBySyndic(long id);
    Immeuble findOne(Long id);
    List<Immeuble> delete(Long id);
}

