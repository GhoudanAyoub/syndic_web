package com.syndicg5.web.rest;

import com.syndicg5.domain.Appartement;
import com.syndicg5.repository.AppartementRepository;
import com.syndicg5.service.AppartementService;
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
 * REST controller for managing {@link com.syndicg5.domain.Appartement}.
 */
@RestController
@RequestMapping("/api")
public class AppartementResource {

    private final Logger log = LoggerFactory.getLogger(AppartementResource.class);

    private static final String ENTITY_NAME = "appartement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppartementService appartementService;

    private final AppartementRepository appartementRepository;

    public AppartementResource(AppartementService appartementService, AppartementRepository appartementRepository) {
        this.appartementService = appartementService;
        this.appartementRepository = appartementRepository;
    }

    /**
     * {@code POST  /appartements} : Create a new appartement.
     *
     * @param appartement the appartement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appartement, or with status {@code 400 (Bad Request)} if the appartement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appartements")
    public ResponseEntity<Appartement> createAppartement(@RequestBody Appartement appartement) throws URISyntaxException {
        log.debug("REST request to save Appartement : {}", appartement);
        if (appartement.getId() != null) {
            throw new BadRequestAlertException("A new appartement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Appartement result = appartementService.save(appartement);
        return ResponseEntity
            .created(new URI("/api/appartements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /appartements/:id} : Updates an existing appartement.
     *
     * @param id the id of the appartement to save.
     * @param appartement the appartement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appartement,
     * or with status {@code 400 (Bad Request)} if the appartement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appartement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appartements/{id}")
    public ResponseEntity<Appartement> updateAppartement(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Appartement appartement
    ) throws URISyntaxException {
        log.debug("REST request to update Appartement : {}, {}", id, appartement);
        if (appartement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appartement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appartementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Appartement result = appartementService.update(appartement);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appartement.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /appartements/:id} : Partial updates given fields of an existing appartement, field will ignore if it is null
     *
     * @param id the id of the appartement to save.
     * @param appartement the appartement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appartement,
     * or with status {@code 400 (Bad Request)} if the appartement is not valid,
     * or with status {@code 404 (Not Found)} if the appartement is not found,
     * or with status {@code 500 (Internal Server Error)} if the appartement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/appartements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Appartement> partialUpdateAppartement(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Appartement appartement
    ) throws URISyntaxException {
        log.debug("REST request to partial update Appartement partially : {}, {}", id, appartement);
        if (appartement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appartement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appartementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Appartement> result = appartementService.partialUpdate(appartement);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appartement.getId())
        );
    }

    /**
     * {@code GET  /appartements} : get all the appartements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appartements in body.
     */
    @GetMapping("/appartements")
    public List<Appartement> getAllAppartements() {
        log.debug("REST request to get all Appartements");
        return appartementService.findAll();
    }

    /**
     * {@code GET  /appartements/:id} : get the "id" appartement.
     *
     * @param id the id of the appartement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appartement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appartements/{id}")
    public ResponseEntity<Appartement> getAppartement(@PathVariable String id) {
        log.debug("REST request to get Appartement : {}", id);
        Optional<Appartement> appartement = appartementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appartement);
    }

    /**
     * {@code DELETE  /appartements/:id} : delete the "id" appartement.
     *
     * @param id the id of the appartement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appartements/{id}")
    public ResponseEntity<Void> deleteAppartement(@PathVariable String id) {
        log.debug("REST request to delete Appartement : {}", id);
        appartementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
