package com.syndicg5.service;

import com.syndicg5.domain.Syndic;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Syndic}.
 */
public interface SyndicService {
    /**
     * Save a syndic.
     *
     * @param syndic the entity to save.
     * @return the persisted entity.
     */
    Syndic save(Syndic syndic);

    /**
     * Updates a syndic.
     *
     * @param syndic the entity to update.
     * @return the persisted entity.
     */
    Syndic update(Syndic syndic);

    /**
     * Partially updates a syndic.
     *
     * @param syndic the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Syndic> partialUpdate(Syndic syndic);

    /**
     * Get all the syndics.
     *
     * @return the list of entities.
     */
    List<Syndic> findAll();

    /**
     * Get the "id" syndic.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Syndic> findOne(String id);

    /**
     * Delete the "id" syndic.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
