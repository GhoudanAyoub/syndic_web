package com.syndicg5.service.impl;

import com.syndicg5.model.Appartement;
import com.syndicg5.repository.AppartementRepository;
import com.syndicg5.service.AppartementService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppartementServiceImpl implements AppartementService {

    @Autowired
    AppartementRepository appartementRepository;

    @Override
    public void save(Appartement appartement) {
        appartementRepository.save(appartement);
    }

    @Override
    public void update(Appartement appartement) {
        Appartement a = appartementRepository.getById(appartement.getId());
        if (a != null)
            appartementRepository.save(a);
    }

    @Override
    public List<Appartement> findAll() {
        return appartementRepository.findAll();
    }

    @Override
    public Appartement findOne(Long id) {
        return appartementRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        appartementRepository.deleteById(id);
    }
}
