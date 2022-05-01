package com.syndicg5.service.impl;

import com.syndicg5.model.Resident;
import com.syndicg5.model.Revenu;
import com.syndicg5.repository.RevenuRepository;
import com.syndicg5.service.RevenuService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevenuServiceImpl implements RevenuService {

    @Autowired
    RevenuRepository revenuRepository;

    @Override
    public void save(Revenu revenu) {
        revenuRepository.save(revenu);
    }

    @Override
    public void update(long id, Revenu revenu) {
        Revenu r = revenuRepository.getById(id);
        r.setDescription(revenu.getDescription());
        r.setDate(revenu.getDate());
        r.setImmeuble(revenu.getImmeuble());
        r.setAppartement(revenu.getAppartement());
        r.setMontant(revenu.getMontant());
        revenuRepository.save(r);
    }

    @Override
    public List<Revenu> findAll() {
        return revenuRepository.findAll();
    }

    @Override
    public Revenu findOne(Long id) {
        return revenuRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        revenuRepository.deleteById(id);
    }
}

