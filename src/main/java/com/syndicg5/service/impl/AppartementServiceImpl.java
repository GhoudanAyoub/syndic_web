package com.syndicg5.service.impl;

import com.syndicg5.model.Appartement;
import com.syndicg5.repository.AppartementRepository;
import com.syndicg5.repository.ImmeubleRepository;
import com.syndicg5.service.AppartementService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class AppartementServiceImpl implements AppartementService {

    @Autowired
    AppartementRepository appartementRepository;
    @Autowired
    ImmeubleRepository immeubleRepository;

    @Override
    public List<Appartement> save(Appartement appartement) {
        long syndicId = immeubleRepository.findById(appartement.getImmeuble().getId()).get().getSyndic().getId();
        appartementRepository.save(appartement);
        return appartementRepository.findAllBySyndic(syndicId);
    }

    @Override
    public List<Appartement> update(long id, Appartement appartement) {
        long syndicId = immeubleRepository.findById(appartement.getImmeuble().getId()).get().getSyndic().getId();
        Appartement a = appartementRepository.findById(id).get();
        a.setNumero(appartement.getNumero());
        a.setEtage(appartement.getEtage());
        a.setImmeuble(appartement.getImmeuble());
        a.setResident(appartement.getResident());
        a.setSurface(appartement.getSurface());
        a.setDebut(appartement.getDebut());
        a.setFin(appartement.getFin());
        appartementRepository.save(a);
        return appartementRepository.findAllBySyndic(syndicId);
    }

    @Override
    public void updateAppartementResident(long residentId, long[] appartementId) {
        for(int i = 0; i < appartementId.length; i++)
        appartementRepository.updateAppartementResident(residentId, appartementId[i]);
    }

    @Override
    public List<Appartement> findAllByImmeubleResident(long id, long residentId) {
        return appartementRepository.findAllByImmeubleResident(id, residentId);
    }

    @Override
    public List<Appartement> findAll() {
        return appartementRepository.findAll();
    }

    @Override
    public List<Appartement> findAllBySyndic(long id) {
        return appartementRepository.findAllBySyndic(id);
    }

    @Override
    public List<Appartement> findAllByResident(long id) {
        return appartementRepository.findAllByResident(id);
    }

    @Override
    public List<Appartement> findAllByImmeuble(long id) {
        return appartementRepository.findAllByImmeuble(id);
    }

    @Override
    public List<Appartement> findAppartementByImmeuble(long id) {
        return appartementRepository.findAppartementByImmeuble(id);
    }

    @Override
    public Appartement findOne(Long id) {
        return appartementRepository.findById(id).get();
    }

    @Override
    public List<Appartement> delete(Long id) {
        long syndicId = appartementRepository.findById(id).get().getImmeuble().getSyndic().getId();
        appartementRepository.deleteById(id);
        return appartementRepository.findAllBySyndic(syndicId);
    }
}
