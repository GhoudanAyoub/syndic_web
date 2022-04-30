package com.syndicg5.service.impl;

import com.syndicg5.model.Depense;
import com.syndicg5.repository.DepenseRepository;
import com.syndicg5.service.DepenseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepenseServiceImpl implements DepenseService {

    @Autowired
    DepenseRepository depenseRepository;

    @Override
    public void save(Depense depense) {
        depenseRepository.save(depense);
    }

    @Override
    public void update(Depense depense) {
        Depense d = depenseRepository.getById(depense.getId());
        if (d != null)
            depenseRepository.save(d);
    }

    @Override
    public List<Depense> findAll() {
        return depenseRepository.findAll();
    }

    @Override
    public Depense findOne(Long id) {
        return depenseRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        depenseRepository.deleteById(id);
    }
}
