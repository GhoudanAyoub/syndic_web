package com.syndicg5.service.impl;

import com.syndicg5.domain.Depense;
import com.syndicg5.repository.DepenseRepository;
import com.syndicg5.service.DepenseService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Depense}.
 */
@Service
@Transactional
public class DepenseServiceImpl implements DepenseService {

    private final Logger log = LoggerFactory.getLogger(DepenseServiceImpl.class);

    private final DepenseRepository depenseRepository;

    public DepenseServiceImpl(DepenseRepository depenseRepository) {
        this.depenseRepository = depenseRepository;
    }

    @Override
    public Depense save(Depense depense) {
        log.debug("Request to save Depense : {}", depense);
        return depenseRepository.save(depense);
    }

    @Override
    public Depense update(Depense depense) {
        log.debug("Request to save Depense : {}", depense);
        return depenseRepository.save(depense);
    }

    @Override
    public Optional<Depense> partialUpdate(Depense depense) {
        log.debug("Request to partially update Depense : {}", depense);

        return depenseRepository
            .findById(depense.getId())
            .map(existingDepense -> {
                if (depense.getMontant() != null) {
                    existingDepense.setMontant(depense.getMontant());
                }
                if (depense.getDate() != null) {
                    existingDepense.setDate(depense.getDate());
                }
                if (depense.getDescription() != null) {
                    existingDepense.setDescription(depense.getDescription());
                }

                return existingDepense;
            })
            .map(depenseRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Depense> findAll() {
        log.debug("Request to get all Depenses");
        return depenseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Depense> findOne(Long id) {
        log.debug("Request to get Depense : {}", id);
        return depenseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Depense : {}", id);
        depenseRepository.deleteById(id);
    }
}
