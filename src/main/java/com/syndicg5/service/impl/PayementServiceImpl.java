package com.syndicg5.service.impl;

import com.syndicg5.model.Immeuble;
import com.syndicg5.model.Payement;
import com.syndicg5.repository.PayementRepository;
import com.syndicg5.service.PayementService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayementServiceImpl implements PayementService {

    @Autowired
    PayementRepository payementRepository;

    @Override
    public void save(Payement payement) {
        payementRepository.save(payement);
    }

    @Override
    public void update(Payement payement) {
        Payement p = payementRepository.getById(payement.getId());
        if (p != null)
            payementRepository.save(p);
    }

    @Override
    public List<Payement> findAll() {
        return payementRepository.findAll();
    }

    @Override
    public Payement findOne(Long id) {
        return payementRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        payementRepository.deleteById(id);
    }
}
