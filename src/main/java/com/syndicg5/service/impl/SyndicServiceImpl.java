package com.syndicg5.service.impl;

import com.syndicg5.domain.Syndic;
import com.syndicg5.repository.SyndicRepository;
import com.syndicg5.service.SyndicService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Syndic}.
 */
@Service
@Transactional
public class SyndicServiceImpl implements SyndicService {

    private final Logger log = LoggerFactory.getLogger(SyndicServiceImpl.class);

    private final SyndicRepository syndicRepository;

    public SyndicServiceImpl(SyndicRepository syndicRepository) {
        this.syndicRepository = syndicRepository;
    }

    @Override
    public Syndic save(Syndic syndic) {
        log.debug("Request to save Syndic : {}", syndic);
        return syndicRepository.save(syndic);
    }

    @Override
    public Syndic update(Syndic syndic) {
        log.debug("Request to save Syndic : {}", syndic);
        return syndicRepository.save(syndic);
    }

    @Override
    public Optional<Syndic> partialUpdate(Syndic syndic) {
        log.debug("Request to partially update Syndic : {}", syndic);

        return syndicRepository
            .findById(syndic.getId())
            .map(existingSyndic -> {
                if (syndic.getAdresse() != null) {
                    existingSyndic.setAdresse(syndic.getAdresse());
                }
                if (syndic.getTel() != null) {
                    existingSyndic.setTel(syndic.getTel());
                }
                if (syndic.getDateTravail() != null) {
                    existingSyndic.setDateTravail(syndic.getDateTravail());
                }
                if (syndic.getPhoto() != null) {
                    existingSyndic.setPhoto(syndic.getPhoto());
                }
                if (syndic.getPhotoContentType() != null) {
                    existingSyndic.setPhotoContentType(syndic.getPhotoContentType());
                }

                return existingSyndic;
            })
            .map(syndicRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Syndic> findAll() {
        log.debug("Request to get all Syndics");
        return syndicRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Syndic> findOne(Long id) {
        log.debug("Request to get Syndic : {}", id);
        return syndicRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Syndic : {}", id);
        syndicRepository.deleteById(id);
    }
}
