package com.syndicg5.service.impl;

import com.syndicg5.model.Revenu;
import com.syndicg5.repository.ImmeubleRepository;
import com.syndicg5.repository.RevenuRepository;
import com.syndicg5.service.RevenuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Integer> findRevenusDate(long id){
        return revenuRepository.findRevenuDates(id);
    }

    @Override
    public Map<Integer, Map<Integer, Double>> findRevenusAppartement(long id, int year) {
        Map<Integer, Map<Integer, Double>> map = new HashMap<>();
        for (Object[] o : revenuRepository.findRevenusAppartement(id, year)) {
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
