package com.syndicg5.service.impl;

import com.syndicg5.model.Revenu;
import com.syndicg5.model.Syndic;
import com.syndicg5.repository.SyndicRepository;
import com.syndicg5.service.SyndicService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SyndicServiceImpl implements SyndicService {

    @Autowired
    SyndicRepository syndicRepository;

    @Override
    public void save(Syndic syndic) {
        syndicRepository.save(syndic);
    }

    @Override
    public void update(long id, Syndic syndic) {
        Syndic s = syndicRepository.getById(id);
        s.setNom(syndic.getNom());
        s.setPrenom(syndic.getPrenom());
        s.setEmail(syndic.getEmail());
        s.setMdp(syndic.getMdp());
        s.setTelephone(syndic.getTelephone());
        s.setVille(syndic.getVille());
        s.setPhoto(syndic.getPhoto());
        s.setImmeubles(syndic.getImmeubles());
        syndicRepository.save(s);
    }

    @Override
    public List<Syndic> findAll() {
        return syndicRepository.findAll();
    }

    @Override
    public Syndic findOne(Long id) {
        return syndicRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        syndicRepository.deleteById(id);
    }
}

