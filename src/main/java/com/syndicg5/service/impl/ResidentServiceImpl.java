package com.syndicg5.service.impl;

import com.syndicg5.model.Resident;
import com.syndicg5.model.Syndic;
import com.syndicg5.repository.AppartementRepository;
import com.syndicg5.repository.ResidentRepository;
import com.syndicg5.service.ResidentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidentServiceImpl implements ResidentService {

    @Autowired
    ResidentRepository residentRepository;
    @Autowired
    AppartementRepository appartementRepository;

    @Override
    public Resident save(Resident resident) {
        return residentRepository.save(resident);
    }

    @Override
    public Resident update(long id, Resident resident) {
        Resident r = residentRepository.findById(id).get();
        r.setNom(resident.getNom());
        r.setPrenom(resident.getPrenom());
        r.setEmail(resident.getEmail());
        r.setTelephone(resident.getTelephone());
        r.setVille(resident.getVille());
        r.setPhoto(resident.getPhoto());
        return residentRepository.save(r);
    }

    @Override
    public List<Resident> findAll() {
        return residentRepository.findAll();
    }

    @Override
    public List<Resident> findAllBySyndic(long syndicId) {
        return residentRepository.findAllBySyndic(syndicId);
    }

    @Override
    public Resident findOneByEmail(String email) {
        return residentRepository.findByEmail(email);
    }

    @Override
    public List<Resident> findAllByNom(long syndicId, String nom) {
        return residentRepository.findAllByNom(syndicId, nom);
    }

    @Override
    public Resident findOne(Long id) {
        return residentRepository.findById(id).get();
    }

    @Override
    public void delete(long id) {
        appartementRepository.deleteAllAppartementResident(id);
        residentRepository.deleteById(id);
    }

    @Override
    public Integer nombreResident() {

        return residentRepository.nombreResident();
    }

    @Override
    public Resident checklogin(String email, String mdp) {
        return residentRepository.check(email,mdp);
    }

    @Override
    public void updatePassword(long id, Resident resident) {
        Resident r = residentRepository.findById(id).get();
        r.setMdp(resident.getMdp());
        residentRepository.save(r);
    }
}
