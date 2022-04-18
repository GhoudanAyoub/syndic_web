package com.syndicg5.service;

import com.syndicg5.domain.Categorie;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Categorie}.
 */
public interface CategorieService {
    /**
     * Save a categorie.
     *
     * @param categorie the entity to save.
     * @return the persisted entity.
     */
    Categorie save(Categorie categorie);

    /**
     * Updates a categorie.
     *
     * @param categorie the entity to update.
     * @return the persisted entity.
     */
    Categorie update(Categorie categorie);

    /**
     * Partially updates a categorie.
     *
     * @param categorie the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Categorie> partialUpdate(Categorie categorie);

    /**
     * Get all the categories.
     *
     * @return the list of entities.
     */
    List<Categorie> findAll();

    /**
     * Get the "id" categorie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Categorie> findOne(String id);

    /**
     * Delete the "id" categorie.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
