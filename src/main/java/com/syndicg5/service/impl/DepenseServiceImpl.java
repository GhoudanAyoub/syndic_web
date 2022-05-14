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
    public List<Depense> save(Depense depense) {
        long syndicId = immeubleRepository.findById(depense.getImmeuble().getId()).get().getSyndic().getId();
        depenseRepository.save(depense);
        return depenseRepository.findDepensesBySyndic(syndicId);
    }

    @Override
    public List<Depense> update(long id, Depense depense) {
        long syndicId = immeubleRepository.findById(depense.getImmeuble().getId()).get().getSyndic().getId();
        Depense d = depenseRepository.findById(id).get();
        d.setDescription(depense.getDescription());
        d.setCategorie(depense.getCategorie());
        d.setDate(depense.getDate());
        d.setImmeuble(depense.getImmeuble());
        d.setMontant(depense.getMontant());
        depenseRepository.save(d);
        return depenseRepository.findDepensesBySyndic(syndicId);
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
    public List<Depense> delete(Long id) {
        long syndicId = depenseRepository.findById(id).get().getImmeuble().getSyndic().getId();
        depenseRepository.deleteById(id);
        return depenseRepository.findDepensesBySyndic(id);
    }
}
