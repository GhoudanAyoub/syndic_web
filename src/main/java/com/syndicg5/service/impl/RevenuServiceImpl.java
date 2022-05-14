package com.syndicg5.service.impl;

import com.syndicg5.model.Resident;
import com.syndicg5.model.Revenu;
import com.syndicg5.repository.ImmeubleRepository;
import com.syndicg5.repository.RevenuRepository;
import com.syndicg5.service.RevenuService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevenuServiceImpl implements RevenuService {

    @Autowired
    RevenuRepository revenuRepository;
    @Autowired
    ImmeubleRepository immeubleRepository;

    @Override
    public List<Revenu> save(Revenu revenu) {
        long syndicId = immeubleRepository.findById(revenu.getImmeuble().getId()).get().getSyndic().getId();
        revenuRepository.save(revenu);
        return revenuRepository.findRevenusBySyndic(syndicId);
    }

    @Override
    public List<Revenu> update(long id, Revenu revenu) {
        long syndicId = immeubleRepository.findById(revenu.getImmeuble().getId()).get().getSyndic().getId();
        Revenu r = revenuRepository.findById(id).get();
        r.setDescription(revenu.getDescription());
        r.setDate(revenu.getDate());
        r.setImmeuble(revenu.getImmeuble());
        r.setAppartement(revenu.getAppartement());
        r.setMontant(revenu.getMontant());
        revenuRepository.save(r);
        return revenuRepository.findRevenusBySyndic(syndicId);
    }

    @Override
    public List<Revenu> findAll() {
        return revenuRepository.findAll();
    }

    @Override
    public List<Revenu> findRevenusByImmeuble(long id) {
        return revenuRepository.findRevenusByImmeuble(id);
    }

    @Override
    public List<Revenu> findRevenusByAppartement(long id) {
        return revenuRepository.findRevenusByAppartement(id);
    }

    @Override
    public List<Revenu> findRevenusBySyndic(long id) {
        return revenuRepository.findRevenusBySyndic(id);
    }

    @Override
    public Revenu findOne(Long id) {
        return revenuRepository.findById(id).get();
    }

    @Override
    public List<Revenu> delete(Long id) {
        long syndicId = revenuRepository.findById(id).get().getImmeuble().getSyndic().getId();
        revenuRepository.deleteById(id);
        return revenuRepository.findRevenusBySyndic(syndicId);
    }
}

