package com.syndicg5.service;

import com.syndicg5.domain.Payement;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Payement}.
 */
public interface PayementService {
    /**
     * Save a payement.
     *
     * @param payement the entity to save.
     * @return the persisted entity.
     */
    Payement save(Payement payement);

    /**
     * Updates a payement.
     *
     * @param payement the entity to update.
     * @return the persisted entity.
     */
    Payement update(Payement payement);

    /**
     * Partially updates a payement.
     *
     * @param payement the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Payement> partialUpdate(Payement payement);

    /**
     * Get all the payements.
     *
     * @return the list of entities.
     */
    List<Payement> findAll();

    /**
     * Get the "id" payement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Payement> findOne(String id);

    /**
     * Delete the "id" payement.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
