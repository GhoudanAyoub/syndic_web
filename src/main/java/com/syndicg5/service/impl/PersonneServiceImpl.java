package com.syndicg5.service.impl;

import com.syndicg5.domain.Personne;
import com.syndicg5.repository.PersonneRepository;
import com.syndicg5.service.PersonneService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Personne}.
 */
@Service
public class PersonneServiceImpl implements PersonneService {

    private final Logger log = LoggerFactory.getLogger(PersonneServiceImpl.class);

    private final PersonneRepository personneRepository;

    public PersonneServiceImpl(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    @Override
    public Personne save(Personne personne) {
        log.debug("Request to save Personne : {}", personne);
        return personneRepository.save(personne);
    }

    @Override
    public Personne update(Personne personne) {
        log.debug("Request to save Personne : {}", personne);
        return personneRepository.save(personne);
    }

    @Override
    public Optional<Personne> partialUpdate(Personne personne) {
        log.debug("Request to partially update Personne : {}", personne);

        return personneRepository
            .findById(personne.getId())
            .map(existingPersonne -> {
                if (personne.getNom() != null) {
                    existingPersonne.setNom(personne.getNom());
                }
                if (personne.getPrenom() != null) {
                    existingPersonne.setPrenom(personne.getPrenom());
                }
                if (personne.getEmail() != null) {
                    existingPersonne.setEmail(personne.getEmail());
                }
                if (personne.getMotPasse() != null) {
                    existingPersonne.setMotPasse(personne.getMotPasse());
                }
                if (personne.getAdresse() != null) {
                    existingPersonne.setAdresse(personne.getAdresse());
                }

                return existingPersonne;
            })
            .map(personneRepository::save);
    }

    @Override
    public List<Personne> findAll() {
        log.debug("Request to get all Personnes");
        return personneRepository.findAll();
    }

    /**
     *  Get all the personnes where Resident is {@code null}.
     *  @return the list of entities.
     */

    public List<Personne> findAllWhereResidentIsNull() {
        log.debug("Request to get all personnes where Resident is null");
        return StreamSupport
            .stream(personneRepository.findAll().spliterator(), false)
            .filter(personne -> personne.getResident() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get all the personnes where Syndic is {@code null}.
     *  @return the list of entities.
     */

    public List<Personne> findAllWhereSyndicIsNull() {
        log.debug("Request to get all personnes where Syndic is null");
        return StreamSupport
            .stream(personneRepository.findAll().spliterator(), false)
            .filter(personne -> personne.getSyndic() == null)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Personne> findOne(String id) {
        log.debug("Request to get Personne : {}", id);
        return personneRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Personne : {}", id);
        personneRepository.deleteById(id);
    }
}
