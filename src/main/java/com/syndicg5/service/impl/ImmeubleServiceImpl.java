package com.syndicg5.service.impl;

import com.syndicg5.model.Depense;
import com.syndicg5.model.Immeuble;
import com.syndicg5.model.Revenu;
import com.syndicg5.repository.ImmeubleRepository;
import com.syndicg5.service.ImmeubleService;

import java.util.*;

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
        Immeuble i = immeubleRepository.findById(id).get();
        i.setNom(immeuble.getNom());
        i.setNumero(immeuble.getNumero());
        i.setAdresse(immeuble.getAdresse());
        i.setVille(immeuble.getVille());
        i.setEtages(immeuble.getEtages());
        i.setPhoto(immeuble.getPhoto());
        i.setSyndic(immeuble.getSyndic());
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
    public List<Immeuble> findAllByNom(long syndicId, String nom) {
        return immeubleRepository.findAllByNom(syndicId, nom);
    }

    @Override
    public List<Immeuble> findAllByResidentEmail(String email) {
        return immeubleRepository.findAllByResident(email);
    }

    @Override
    public Immeuble findOne(Long id) {
        return immeubleRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        immeubleRepository.deleteById(id);
    }

    @Override
    public Integer nomreImmeuble() {

        return immeubleRepository.nomreImmeuble();
    }

    @Override
    public SortedSet<Integer> findImmeubleDates(long id) {
        List<Integer> years = new ArrayList<>();
        if (!immeubleRepository.findDepenseDates(id).isEmpty()) {
            for (Integer i : immeubleRepository.findDepenseDates(id)) {
                years.add(i);
            }
        }

        if (!immeubleRepository.findRevenuDates(id).isEmpty()) {
            for (Integer i : immeubleRepository.findRevenuDates(id)) {
                years.add(i);
            }
        }

        SortedSet<Integer> filteredYears = new TreeSet<>(years);
        return filteredYears;
    }

    @Override
    public Map<Integer, Map<Integer, Double>> findRevenusImmeuble(long id, int year) {
        Map<Integer, Map<Integer, Double>> map = new HashMap<>();
        for (Object[] o : immeubleRepository.findRevenusImmeuble(id, year)) {
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

    @Override
    public Map<String, Map<Integer, Double>> findDepensesImmeuble(long id, int year) {
        Map<String, Map<Integer, Double>> map = new HashMap<>();
        for (Object[] o : immeubleRepository.findDepensesImmeuble(id, year)) {
            for (int i = 0; i < o.length; i++) {
                if (!map.containsKey(o[0])) {
                    Map<Integer, Double> map2 = new HashMap<>();
                    map2.put((Integer) o[1], (Double) o[2]);
                    map.put((String) o[0], map2);
                }else {
                    Map<Integer, Double> map2 = map.get(o[0]);
                    map2.put((Integer) o[1], (Double) o[2]);
                    map.replace((String) o[0], map2);
                }
            }
        }

        for(String key : map.keySet()) {
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
        for(String key : map.keySet()) {
            System.out.println(key + " : " + map.get(key));
        }
        return map;
    }

    @Override
    public List<Immeuble> findAllByResident(long id) {
        return immeubleRepository.findAllResident(id);
    }
}
