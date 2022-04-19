package com.syndicg5.web.rest;

import com.syndicg5.domain.Depense;
import com.syndicg5.repository.DepenseRepository;
import com.syndicg5.service.DepenseService;
import com.syndicg5.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.syndicg5.domain.Depense}.
 */
@RestController
@RequestMapping("/api")
public class DepenseResource {

    private final Logger log = LoggerFactory.getLogger(DepenseResource.class);

    private static final String ENTITY_NAME = "depense";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepenseService depenseService;

    private final DepenseRepository depenseRepository;

    public DepenseResource(DepenseService depenseService, DepenseRepository depenseRepository) {
        this.depenseService = depenseService;
        this.depenseRepository = depenseRepository;
    }

    /**
     * {@code POST  /depenses} : Create a new depense.
     *
     * @param depense the depense to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new depense, or with status {@code 400 (Bad Request)} if the depense has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/depenses")
    public ResponseEntity<Depense> createDepense(@RequestBody Depense depense) throws URISyntaxException {
        log.debug("REST request to save Depense : {}", depense);
        if (depense.getId() != null) {
            throw new BadRequestAlertException("A new depense cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Depense result = depenseService.save(depense);
        return ResponseEntity
            .created(new URI("/api/depenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /depenses/:id} : Updates an existing depense.
     *
     * @param id the id of the depense to save.
     * @param depense the depense to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depense,
     * or with status {@code 400 (Bad Request)} if the depense is not valid,
     * or with status {@code 500 (Internal Server Error)} if the depense couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/depenses/{id}")
    public ResponseEntity<Depense> updateDepense(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Depense depense
    ) throws URISyntaxException {
        log.debug("REST request to update Depense : {}, {}", id, depense);
        if (depense.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, depense.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!depenseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Depense result = depenseService.update(depense);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, depense.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /depenses/:id} : Partial updates given fields of an existing depense, field will ignore if it is null
     *
     * @param id the id of the depense to save.
     * @param depense the depense to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depense,
     * or with status {@code 400 (Bad Request)} if the depense is not valid,
     * or with status {@code 404 (Not Found)} if the depense is not found,
     * or with status {@code 500 (Internal Server Error)} if the depense couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/depenses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Depense> partialUpdateDepense(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Depense depense
    ) throws URISyntaxException {
        log.debug("REST request to partial update Depense partially : {}, {}", id, depense);
        if (depense.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, depense.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!depenseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Depense> result = depenseService.partialUpdate(depense);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, depense.getId()));
    }

    /**
     * {@code GET  /depenses} : get all the depenses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of depenses in body.
     */
    @GetMapping("/depenses")
    public List<Depense> getAllDepenses() {
        log.debug("REST request to get all Depenses");
        return depenseService.findAll();
    }

    /**
     * {@code GET  /depenses/:id} : get the "id" depense.
     *
     * @param id the id of the depense to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the depense, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/depenses/{id}")
    public ResponseEntity<Depense> getDepense(@PathVariable String id) {
        log.debug("REST request to get Depense : {}", id);
        Optional<Depense> depense = depenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depense);
    }

    /**
     * {@code DELETE  /depenses/:id} : delete the "id" depense.
     *
     * @param id the id of the depense to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/depenses/{id}")
    public ResponseEntity<Void> deleteDepense(@PathVariable String id) {
        log.debug("REST request to delete Depense : {}", id);
        depenseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
