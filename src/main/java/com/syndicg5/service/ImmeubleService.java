package com.syndicg5.service;

import com.syndicg5.domain.Immeuble;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Immeuble}.
 */
public interface ImmeubleService {
    /**
     * Save a immeuble.
     *
     * @param immeuble the entity to save.
     * @return the persisted entity.
     */
    Immeuble save(Immeuble immeuble);

    /**
     * Updates a immeuble.
     *
     * @param immeuble the entity to update.
     * @return the persisted entity.
     */
    Immeuble update(Immeuble immeuble);

    /**
     * Partially updates a immeuble.
     *
     * @param immeuble the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Immeuble> partialUpdate(Immeuble immeuble);

    /**
     * Get all the immeubles.
     *
     * @return the list of entities.
     */
    List<Immeuble> findAll();

    /**
     * Get the "id" immeuble.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Immeuble> findOne(Long id);

    /**
     * Delete the "id" immeuble.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
