package com.syndicg5.service.impl;

import com.syndicg5.domain.Appartement;
import com.syndicg5.repository.AppartementRepository;
import com.syndicg5.service.AppartementService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Appartement}.
 */
@Service
@Transactional
public class AppartementServiceImpl implements AppartementService {

    private final Logger log = LoggerFactory.getLogger(AppartementServiceImpl.class);

    private final AppartementRepository appartementRepository;

    public AppartementServiceImpl(AppartementRepository appartementRepository) {
        this.appartementRepository = appartementRepository;
    }

    @Override
    public Appartement save(Appartement appartement) {
        log.debug("Request to save Appartement : {}", appartement);
        return appartementRepository.save(appartement);
    }

    @Override
    public Appartement update(Appartement appartement) {
        log.debug("Request to save Appartement : {}", appartement);
        return appartementRepository.save(appartement);
    }

    @Override
    public Optional<Appartement> partialUpdate(Appartement appartement) {
        log.debug("Request to partially update Appartement : {}", appartement);

        return appartementRepository
            .findById(appartement.getId())
            .map(existingAppartement -> {
                if (appartement.getNumero() != null) {
                    existingAppartement.setNumero(appartement.getNumero());
                }
                if (appartement.getEtage() != null) {
                    existingAppartement.setEtage(appartement.getEtage());
                }
                if (appartement.getSurface() != null) {
                    existingAppartement.setSurface(appartement.getSurface());
                }

                return existingAppartement;
            })
            .map(appartementRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appartement> findAll() {
        log.debug("Request to get all Appartements");
        return appartementRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Appartement> findOne(Long id) {
        log.debug("Request to get Appartement : {}", id);
        return appartementRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Appartement : {}", id);
        appartementRepository.deleteById(id);
    }
}
