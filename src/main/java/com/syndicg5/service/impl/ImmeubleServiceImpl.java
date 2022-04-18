package com.syndicg5.service.impl;

import com.syndicg5.domain.Immeuble;
import com.syndicg5.repository.ImmeubleRepository;
import com.syndicg5.service.ImmeubleService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Immeuble}.
 */
@Service
public class ImmeubleServiceImpl implements ImmeubleService {

    private final Logger log = LoggerFactory.getLogger(ImmeubleServiceImpl.class);

    private final ImmeubleRepository immeubleRepository;

    public ImmeubleServiceImpl(ImmeubleRepository immeubleRepository) {
        this.immeubleRepository = immeubleRepository;
    }

    @Override
    public Immeuble save(Immeuble immeuble) {
        log.debug("Request to save Immeuble : {}", immeuble);
        return immeubleRepository.save(immeuble);
    }

    @Override
    public Immeuble update(Immeuble immeuble) {
        log.debug("Request to save Immeuble : {}", immeuble);
        return immeubleRepository.save(immeuble);
    }

    @Override
    public Optional<Immeuble> partialUpdate(Immeuble immeuble) {
        log.debug("Request to partially update Immeuble : {}", immeuble);

        return immeubleRepository
            .findById(immeuble.getId())
            .map(existingImmeuble -> {
                if (immeuble.getLibelle() != null) {
                    existingImmeuble.setLibelle(immeuble.getLibelle());
                }
                if (immeuble.getAdresse() != null) {
                    existingImmeuble.setAdresse(immeuble.getAdresse());
                }
                if (immeuble.getVille() != null) {
                    existingImmeuble.setVille(immeuble.getVille());
                }
                if (immeuble.getNumero() != null) {
                    existingImmeuble.setNumero(immeuble.getNumero());
                }
                if (immeuble.getNbEtages() != null) {
                    existingImmeuble.setNbEtages(immeuble.getNbEtages());
                }

                return existingImmeuble;
            })
            .map(immeubleRepository::save);
    }

    @Override
    public List<Immeuble> findAll() {
        log.debug("Request to get all Immeubles");
        return immeubleRepository.findAll();
    }

    @Override
    public Optional<Immeuble> findOne(String id) {
        log.debug("Request to get Immeuble : {}", id);
        return immeubleRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Immeuble : {}", id);
        immeubleRepository.deleteById(id);
    }
}
