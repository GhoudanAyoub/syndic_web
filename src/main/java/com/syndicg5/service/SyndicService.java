package com.syndicg5.service;

import com.syndicg5.model.Syndic;

import java.util.List;

public interface SyndicService {
    void save(Syndic syndic);
    void update(long id, Syndic syndic);
    List<Syndic> findAll();
    Syndic findOne(Long id);
    void delete(Long id);
}

