package com.syndicg5.service.impl;

import com.syndicg5.model.Revenu;
import com.syndicg5.repository.ImmeubleRepository;
import com.syndicg5.repository.RevenuRepository;
import com.syndicg5.service.RevenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevenuServiceImpl implements RevenuService {

    @Autowired
    RevenuRepository revenuRepository;
    @Autowired
    ImmeubleRepository immeubleRepository;

    @Override
    public void save(Revenu revenu) {
        revenuRepository.save(revenu);
    }

    @Override
    public void update(long id, Revenu revenu) {
        Revenu r = revenuRepository.findById(id).get();
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
    public void delete(Long id) {
        revenuRepository.deleteById(id);
    }

    @Override
    public double findRevenueMax() {
        return revenuRepository.findRevenueMax();
    }

    @Override
    public List<Object[]> revenuParAnnee() {

        return revenuRepository.revenuParAnnee();
    }

    @Override
    public List<Object[]> revenuParMontant() {

        return revenuRepository.revenuParMontant();
    }

    @Override
    public List<Revenu> findRevenusByResident(long id) {
        return revenuRepository.findRevenusByResident(id);
    }

    @Override
    public List<Revenu> findRevenusByresident(long id) {
        return revenuRepository.findRevenusByresident(id);
    }

    @Override
    public List<Integer> findRevenusDate(long id) {
        return revenuRepository.findRevenuDates(id);
    }
}
