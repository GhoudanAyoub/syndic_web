package com.syndicg5.service.impl;

import com.syndicg5.model.Appartement;
import com.syndicg5.repository.AppartementRepository;
import com.syndicg5.repository.ImmeubleRepository;
import com.syndicg5.service.AppartementService;

import java.util.*;

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
    public void save(Appartement appartement) {
        appartementRepository.save(appartement);
    }
    @Override
    public List<Appartement> getAppartementByImmeuble(Long id) {
        return appartementRepository.findAppartementByImmeuble(id);
    }

    @Override
    public void update(long id, Appartement appartement) {
        Appartement a = appartementRepository.findById(id).get();
        a.setNumero(appartement.getNumero());
        a.setEtage(appartement.getEtage());
        a.setImmeuble(appartement.getImmeuble());
        a.setResident(appartement.getResident());
        a.setSurface(appartement.getSurface());
        a.setDebut(appartement.getDebut());
        a.setFin(appartement.getFin());
        appartementRepository.save(a);
    }

    @Override
    public void updatePartial(long id, Appartement appartement) {
        Appartement a = appartementRepository.findById(id).get();
        a.setDebut(appartement.getDebut());
        a.setFin(appartement.getFin());
        appartementRepository.save(a);
    }

    @Override
    public void updateAppartementResident(long residentId, long appartementId, Appartement appartement) {
        Appartement a = appartementRepository.findById(appartementId).get();
        a.setDebut(appartement.getDebut());
        a.setFin(appartement.getFin());
        appartementRepository.save(a);
        appartementRepository.updateAppartementResident(residentId, appartementId);
    }

    @Override
    public void updateAppartementsResident(long residentId, long[] appartementId) {
        for(int i = 0; i < appartementId.length; i++)
        appartementRepository.updateAppartementResident(residentId, appartementId[i]);
    }

    @Override
    public void deleteAppartementResident(long appartementId) {
        appartementRepository.deleteAppartementResident(appartementId);
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
    public List<Appartement> findAllEmptyByImmeuble(long id) {
        return appartementRepository.findAllEmptyByImmeuble(id);
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
    public void delete(Long id) {
        appartementRepository.deleteById(id);
    }

    @Override
    public SortedSet<Integer> findAppartementDates(long id) {
        List<Integer> years = new ArrayList<>();
        if (!appartementRepository.findRevenuDates(id).isEmpty()) {
            for (Integer i : appartementRepository.findRevenuDates(id)) {
                years.add(i);
            }
        }

        SortedSet<Integer> filteredYears = new TreeSet<>(years);
        return filteredYears;
    }

    @Override
    public Map<Integer, Map<Integer, Double>> findRevenusAppartement(long id, int year) {
        Map<Integer, Map<Integer, Double>> map = new HashMap<>();
        for (Object[] o : appartementRepository.findRevenusAppartement(id, year)) {
            for (int i = 0; i < o.length; i++) {
                if (!map.containsKey(o[0])) {
                    Map<Integer, Double> map2 = new HashMap<>();
                    map2.put((Integer) o[1], (Double) o[2]);
                    map.put((Integer) o[0], map2);
                }else {
                    Map<Integer, Double> map2 = map.get(o[0]);
                    map2.put((Integer) o[1], (Double) o[2]);
                    map.replace((Integer) o[0], map2);
                }
            }
        }

        for(Integer key : map.keySet()) {
            Map<Integer, Double> map2 = map.get(key);
            System.out.println(key);
            for(int i = 1; i <= 12; i++) {
                boolean exists = false;
                for(Integer month : map2.keySet()) {
                    if(i == month) {
                        exists = true;
                    }
                }
                if(!exists) {
                    map2.put(i, 0.0);
                }
            }
        }


        System.out.println("Showing map :");
        for(Integer key : map.keySet()) {
            System.out.println(key + " : " + map.get(key));
        }
        return map;
    }
}
