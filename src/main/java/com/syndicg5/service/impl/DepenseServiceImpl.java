package com.syndicg5.service.impl;

import com.syndicg5.model.Depense;
import com.syndicg5.repository.CategorieRepository;
import com.syndicg5.repository.DepenseRepository;
import com.syndicg5.repository.ImmeubleRepository;
import com.syndicg5.service.DepenseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepenseServiceImpl implements DepenseService {

    @Autowired
    DepenseRepository depenseRepository;
    @Autowired
    ImmeubleRepository immeubleRepository;

    @Override
    public void save(Depense depense) {
        depenseRepository.save(depense);
    }

    @Override
    public void update(long id, Depense depense) {
        Depense d = depenseRepository.findById(id).get();
        d.setDescription(depense.getDescription());
        d.setCategorie(depense.getCategorie());
        d.setDate(depense.getDate());
        d.setImmeuble(depense.getImmeuble());
        d.setMontant(depense.getMontant());
        depenseRepository.save(d);
    }

    @Override
    public List<Depense> findAll() {
        return depenseRepository.findAll();
    }

    @Override
    public List<Depense> findDepensesByImmeuble(long id) {
        return depenseRepository.findDepensesByImmeuble(id);
    }

    @Override
    public List<Depense> findDepensesBySyndic(long id) {
        return depenseRepository.findDepensesBySyndic(id);
    }

    @Override
    public Depense findOne(Long id) {
        return depenseRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        depenseRepository.deleteById(id);
    }

    @Override
    public double finddepenseMax() {
        return depenseRepository.finddepenseMax();
    }

    @Override
    public List<Object[]> depenseParAnnee() {

        return depenseRepository.depenseParAnnee();
    }

    @Override
    public List<Object[]> depenseParMontant() {

        return depenseRepository.depenseParMontant();
    }

}
