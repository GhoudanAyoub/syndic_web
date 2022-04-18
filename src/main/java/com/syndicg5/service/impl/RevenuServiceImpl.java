package com.syndicg5.service.impl;

import com.syndicg5.domain.Revenu;
import com.syndicg5.repository.RevenuRepository;
import com.syndicg5.service.RevenuService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Revenu}.
 */
@Service
public class RevenuServiceImpl implements RevenuService {

    private final Logger log = LoggerFactory.getLogger(RevenuServiceImpl.class);

    private final RevenuRepository revenuRepository;

    public RevenuServiceImpl(RevenuRepository revenuRepository) {
        this.revenuRepository = revenuRepository;
    }

    @Override
    public Revenu save(Revenu revenu) {
        log.debug("Request to save Revenu : {}", revenu);
        return revenuRepository.save(revenu);
    }

    @Override
    public Revenu update(Revenu revenu) {
        log.debug("Request to save Revenu : {}", revenu);
        return revenuRepository.save(revenu);
    }

    @Override
    public Optional<Revenu> partialUpdate(Revenu revenu) {
        log.debug("Request to partially update Revenu : {}", revenu);

        return revenuRepository
            .findById(revenu.getId())
            .map(existingRevenu -> {
                if (revenu.getMontant() != null) {
                    existingRevenu.setMontant(revenu.getMontant());
                }
                if (revenu.getDate() != null) {
                    existingRevenu.setDate(revenu.getDate());
                }
                if (revenu.getDescription() != null) {
                    existingRevenu.setDescription(revenu.getDescription());
                }

                return existingRevenu;
            })
            .map(revenuRepository::save);
    }

    @Override
    public List<Revenu> findAll() {
        log.debug("Request to get all Revenus");
        return revenuRepository.findAll();
    }

    @Override
    public Optional<Revenu> findOne(String id) {
        log.debug("Request to get Revenu : {}", id);
        return revenuRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Revenu : {}", id);
        revenuRepository.deleteById(id);
    }
}
