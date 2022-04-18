package com.syndicg5.service.impl;

import com.syndicg5.domain.Payement;
import com.syndicg5.repository.PayementRepository;
import com.syndicg5.service.PayementService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Payement}.
 */
@Service
public class PayementServiceImpl implements PayementService {

    private final Logger log = LoggerFactory.getLogger(PayementServiceImpl.class);

    private final PayementRepository payementRepository;

    public PayementServiceImpl(PayementRepository payementRepository) {
        this.payementRepository = payementRepository;
    }

    @Override
    public Payement save(Payement payement) {
        log.debug("Request to save Payement : {}", payement);
        return payementRepository.save(payement);
    }

    @Override
    public Payement update(Payement payement) {
        log.debug("Request to save Payement : {}", payement);
        return payementRepository.save(payement);
    }

    @Override
    public Optional<Payement> partialUpdate(Payement payement) {
        log.debug("Request to partially update Payement : {}", payement);

        return payementRepository
            .findById(payement.getId())
            .map(existingPayement -> {
                if (payement.getMontant() != null) {
                    existingPayement.setMontant(payement.getMontant());
                }
                if (payement.getDate() != null) {
                    existingPayement.setDate(payement.getDate());
                }
                if (payement.getDescription() != null) {
                    existingPayement.setDescription(payement.getDescription());
                }

                return existingPayement;
            })
            .map(payementRepository::save);
    }

    @Override
    public List<Payement> findAll() {
        log.debug("Request to get all Payements");
        return payementRepository.findAll();
    }

    @Override
    public Optional<Payement> findOne(String id) {
        log.debug("Request to get Payement : {}", id);
        return payementRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Payement : {}", id);
        payementRepository.deleteById(id);
    }
}
