package com.syndicg5.service;

import com.syndicg5.domain.Revenu;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Revenu}.
 */
public interface RevenuService {
    /**
     * Save a revenu.
     *
     * @param revenu the entity to save.
     * @return the persisted entity.
     */
    Revenu save(Revenu revenu);

    /**
     * Updates a revenu.
     *
     * @param revenu the entity to update.
     * @return the persisted entity.
     */
    Revenu update(Revenu revenu);

    /**
     * Partially updates a revenu.
     *
     * @param revenu the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Revenu> partialUpdate(Revenu revenu);

    /**
     * Get all the revenus.
     *
     * @return the list of entities.
     */
    List<Revenu> findAll();

    /**
     * Get the "id" revenu.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Revenu> findOne(String id);

    /**
     * Delete the "id" revenu.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
