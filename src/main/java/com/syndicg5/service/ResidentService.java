package com.syndicg5.service;

import com.syndicg5.domain.Resident;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Resident}.
 */
public interface ResidentService {
    /**
     * Save a resident.
     *
     * @param resident the entity to save.
     * @return the persisted entity.
     */
    Resident save(Resident resident);

    /**
     * Updates a resident.
     *
     * @param resident the entity to update.
     * @return the persisted entity.
     */
    Resident update(Resident resident);

    /**
     * Partially updates a resident.
     *
     * @param resident the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Resident> partialUpdate(Resident resident);

    /**
     * Get all the residents.
     *
     * @return the list of entities.
     */
    List<Resident> findAll();

    /**
     * Get the "id" resident.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Resident> findOne(String id);

    /**
     * Delete the "id" resident.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
