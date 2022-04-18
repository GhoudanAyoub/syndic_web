package com.syndicg5.service;

import com.syndicg5.domain.Appartement;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Appartement}.
 */
public interface AppartementService {
    /**
     * Save a appartement.
     *
     * @param appartement the entity to save.
     * @return the persisted entity.
     */
    Appartement save(Appartement appartement);

    /**
     * Updates a appartement.
     *
     * @param appartement the entity to update.
     * @return the persisted entity.
     */
    Appartement update(Appartement appartement);

    /**
     * Partially updates a appartement.
     *
     * @param appartement the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Appartement> partialUpdate(Appartement appartement);

    /**
     * Get all the appartements.
     *
     * @return the list of entities.
     */
    List<Appartement> findAll();

    /**
     * Get the "id" appartement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Appartement> findOne(String id);

    /**
     * Delete the "id" appartement.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
