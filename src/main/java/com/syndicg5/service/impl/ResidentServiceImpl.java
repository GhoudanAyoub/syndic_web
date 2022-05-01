package com.syndicg5.service.impl;

import com.syndicg5.model.Resident;
import com.syndicg5.repository.ResidentRepository;
import com.syndicg5.service.ResidentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidentServiceImpl implements ResidentService {

    @Autowired
    ResidentRepository residentRepository;

    @Override
    public void save(Resident resident) {
        residentRepository.save(resident);
    }

    @Override
    public void update(long id, Resident resident) {
        Resident r = residentRepository.getById(id);
        r.setNom(resident.getNom());
        r.setPrenom(resident.getPrenom());
        r.setEmail(resident.getEmail());
        r.setMdp(resident.getMdp());
        r.setTelephone(resident.getTelephone());
        r.setVille(resident.getVille());
        r.setPhoto(resident.getPhoto());
        r.setAppartements(resident.getAppartements());
        residentRepository.save(r);
    }

    @Override
    public List<Resident> findAll() {
        return residentRepository.findAll();
    }

    @Override
    public Resident findOne(Long id) {
        return residentRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        residentRepository.deleteById(id);
    }
}
