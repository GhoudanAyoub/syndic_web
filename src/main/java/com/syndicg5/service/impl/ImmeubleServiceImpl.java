package com.syndicg5.service.impl;

import com.syndicg5.model.Depense;
import com.syndicg5.model.Immeuble;
import com.syndicg5.repository.ImmeubleRepository;
import com.syndicg5.service.ImmeubleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImmeubleServiceImpl implements ImmeubleService {

    @Autowired
    ImmeubleRepository immeubleRepository;

    @Override
    public void save(Immeuble immeuble) {
        immeubleRepository.save(immeuble);
    }

    @Override
    public void update(long id, Immeuble immeuble) {
        Immeuble i = immeubleRepository.getById(id);
        i.setNom(immeuble.getNom());
        i.setNumero(immeuble.getNumero());
        i.setAdresse(immeuble.getAdresse());
        i.setVille(immeuble.getVille());
        i.setEtages(immeuble.getEtages());
        i.setPhoto(immeuble.getPhoto());
        i.setSyndic(immeuble.getSyndic());
        i.setAppartements(immeuble.getAppartements());
        i.setDepenses(immeuble.getDepenses());
        i.setRevenus(immeuble.getRevenus());
        immeubleRepository.save(i);
    }

    @Override
    public List<Immeuble> findAll() {
        return immeubleRepository.findAll();
    }

    @Override
    public List<Immeuble> findAllBySyndic(long id) {
        return immeubleRepository.findAllBySyndic(id);
    }

    @Override
    public Immeuble findOne(Long id) {
        return immeubleRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        immeubleRepository.deleteById(id);
    }
}
