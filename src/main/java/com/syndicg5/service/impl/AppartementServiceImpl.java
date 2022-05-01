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
    public void update(long id, Appartement appartement) {
        Appartement a = appartementRepository.getById(id);
        a.setNumero(appartement.getNumero());
        a.setEtage(appartement.getEtage());
        a.setImmeuble(appartement.getImmeuble());
        a.setResident(appartement.getResident());
        a.setSurface(appartement.getSurface());
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
