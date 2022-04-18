package com.syndicg5.service;

import com.syndicg5.domain.Depense;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Depense}.
 */
public interface DepenseService {
    /**
     * Save a depense.
     *
     * @param depense the entity to save.
     * @return the persisted entity.
     */
    Depense save(Depense depense);

    /**
     * Updates a depense.
     *
     * @param depense the entity to update.
     * @return the persisted entity.
     */
    Depense update(Depense depense);

    /**
     * Partially updates a depense.
     *
     * @param depense the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Depense> partialUpdate(Depense depense);

    /**
     * Get all the depenses.
     *
     * @return the list of entities.
     */
    List<Depense> findAll();

    /**
     * Get the "id" depense.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Depense> findOne(String id);

    /**
     * Delete the "id" depense.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
