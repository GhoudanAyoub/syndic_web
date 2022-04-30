package com.syndicg5.service.impl;

import com.syndicg5.domain.Resident;
import com.syndicg5.repository.ResidentRepository;
import com.syndicg5.service.ResidentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Resident}.
 */
@Service
@Transactional
public class ResidentServiceImpl implements ResidentService {

    private final Logger log = LoggerFactory.getLogger(ResidentServiceImpl.class);

    private final ResidentRepository residentRepository;

    public ResidentServiceImpl(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Override
    public Resident save(Resident resident) {
        log.debug("Request to save Resident : {}", resident);
        return residentRepository.save(resident);
    }

    @Override
    public Resident update(Resident resident) {
        log.debug("Request to save Resident : {}", resident);
        return residentRepository.save(resident);
    }

    @Override
    public Optional<Resident> partialUpdate(Resident resident) {
        log.debug("Request to partially update Resident : {}", resident);

        return residentRepository
            .findById(resident.getId())
            .map(existingResident -> {
                if (resident.getEmail() != null) {
                    existingResident.setEmail(resident.getEmail());
                }
                if (resident.getMotPasse() != null) {
                    existingResident.setMotPasse(resident.getMotPasse());
                }
                if (resident.getNom() != null) {
                    existingResident.setNom(resident.getNom());
                }
                if (resident.getPrenom() != null) {
                    existingResident.setPrenom(resident.getPrenom());
                }
                if (resident.getAdresse() != null) {
                    existingResident.setAdresse(resident.getAdresse());
                }
                if (resident.getTel() != null) {
                    existingResident.setTel(resident.getTel());
                }
                if (resident.getPhoto() != null) {
                    existingResident.setPhoto(resident.getPhoto());
                }
                if (resident.getPhotoContentType() != null) {
                    existingResident.setPhotoContentType(resident.getPhotoContentType());
                }

                return existingResident;
            })
            .map(residentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resident> findAll() {
        log.debug("Request to get all Residents");
        return residentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Resident> findOne(Long id) {
        log.debug("Request to get Resident : {}", id);
        return residentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Resident : {}", id);
        residentRepository.deleteById(id);
    }
}
