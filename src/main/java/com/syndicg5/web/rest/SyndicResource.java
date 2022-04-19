package com.syndicg5.web.rest;

import com.syndicg5.domain.Syndic;
import com.syndicg5.repository.SyndicRepository;
import com.syndicg5.service.SyndicService;
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
 * REST controller for managing {@link com.syndicg5.domain.Syndic}.
 */
@RestController
@RequestMapping("/api")
public class SyndicResource {

    private final Logger log = LoggerFactory.getLogger(SyndicResource.class);

    private static final String ENTITY_NAME = "syndic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SyndicService syndicService;

    private final SyndicRepository syndicRepository;

    public SyndicResource(SyndicService syndicService, SyndicRepository syndicRepository) {
        this.syndicService = syndicService;
        this.syndicRepository = syndicRepository;
    }

    /**
     * {@code POST  /syndics} : Create a new syndic.
     *
     * @param syndic the syndic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new syndic, or with status {@code 400 (Bad Request)} if the syndic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/syndics")
    public ResponseEntity<Syndic> createSyndic(@RequestBody Syndic syndic) throws URISyntaxException {
        log.debug("REST request to save Syndic : {}", syndic);
        if (syndic.getId() != null) {
            throw new BadRequestAlertException("A new syndic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Syndic result = syndicService.save(syndic);
        return ResponseEntity
            .created(new URI("/api/syndics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /syndics/:id} : Updates an existing syndic.
     *
     * @param id the id of the syndic to save.
     * @param syndic the syndic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated syndic,
     * or with status {@code 400 (Bad Request)} if the syndic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the syndic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/syndics/{id}")
    public ResponseEntity<Syndic> updateSyndic(@PathVariable(value = "id", required = false) final String id, @RequestBody Syndic syndic)
        throws URISyntaxException {
        log.debug("REST request to update Syndic : {}, {}", id, syndic);
        if (syndic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, syndic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!syndicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Syndic result = syndicService.update(syndic);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, syndic.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /syndics/:id} : Partial updates given fields of an existing syndic, field will ignore if it is null
     *
     * @param id the id of the syndic to save.
     * @param syndic the syndic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated syndic,
     * or with status {@code 400 (Bad Request)} if the syndic is not valid,
     * or with status {@code 404 (Not Found)} if the syndic is not found,
     * or with status {@code 500 (Internal Server Error)} if the syndic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/syndics/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Syndic> partialUpdateSyndic(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Syndic syndic
    ) throws URISyntaxException {
        log.debug("REST request to partial update Syndic partially : {}, {}", id, syndic);
        if (syndic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, syndic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!syndicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Syndic> result = syndicService.partialUpdate(syndic);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, syndic.getId()));
    }

    /**
     * {@code GET  /syndics} : get all the syndics.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of syndics in body.
     */
    @GetMapping("/syndics")
    public List<Syndic> getAllSyndics() {
        log.debug("REST request to get all Syndics");
        return syndicService.findAll();
    }

    /**
     * {@code GET  /syndics/:id} : get the "id" syndic.
     *
     * @param id the id of the syndic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the syndic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/syndics/{id}")
    public ResponseEntity<Syndic> getSyndic(@PathVariable String id) {
        log.debug("REST request to get Syndic : {}", id);
        Optional<Syndic> syndic = syndicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(syndic);
    }

    /**
     * {@code DELETE  /syndics/:id} : delete the "id" syndic.
     *
     * @param id the id of the syndic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/syndics/{id}")
    public ResponseEntity<Void> deleteSyndic(@PathVariable String id) {
        log.debug("REST request to delete Syndic : {}", id);
        syndicService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
