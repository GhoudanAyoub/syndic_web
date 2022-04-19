package com.syndicg5.web.rest;

import com.syndicg5.domain.Revenu;
import com.syndicg5.repository.RevenuRepository;
import com.syndicg5.service.RevenuService;
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
 * REST controller for managing {@link com.syndicg5.domain.Revenu}.
 */
@RestController
@RequestMapping("/api")
public class RevenuResource {

    private final Logger log = LoggerFactory.getLogger(RevenuResource.class);

    private static final String ENTITY_NAME = "revenu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RevenuService revenuService;

    private final RevenuRepository revenuRepository;

    public RevenuResource(RevenuService revenuService, RevenuRepository revenuRepository) {
        this.revenuService = revenuService;
        this.revenuRepository = revenuRepository;
    }

    /**
     * {@code POST  /revenus} : Create a new revenu.
     *
     * @param revenu the revenu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new revenu, or with status {@code 400 (Bad Request)} if the revenu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/revenus")
    public ResponseEntity<Revenu> createRevenu(@RequestBody Revenu revenu) throws URISyntaxException {
        log.debug("REST request to save Revenu : {}", revenu);
        if (revenu.getId() != null) {
            throw new BadRequestAlertException("A new revenu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Revenu result = revenuService.save(revenu);
        return ResponseEntity
            .created(new URI("/api/revenus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /revenus/:id} : Updates an existing revenu.
     *
     * @param id the id of the revenu to save.
     * @param revenu the revenu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated revenu,
     * or with status {@code 400 (Bad Request)} if the revenu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the revenu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/revenus/{id}")
    public ResponseEntity<Revenu> updateRevenu(@PathVariable(value = "id", required = false) final String id, @RequestBody Revenu revenu)
        throws URISyntaxException {
        log.debug("REST request to update Revenu : {}, {}", id, revenu);
        if (revenu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, revenu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!revenuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Revenu result = revenuService.update(revenu);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, revenu.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /revenus/:id} : Partial updates given fields of an existing revenu, field will ignore if it is null
     *
     * @param id the id of the revenu to save.
     * @param revenu the revenu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated revenu,
     * or with status {@code 400 (Bad Request)} if the revenu is not valid,
     * or with status {@code 404 (Not Found)} if the revenu is not found,
     * or with status {@code 500 (Internal Server Error)} if the revenu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/revenus/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Revenu> partialUpdateRevenu(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Revenu revenu
    ) throws URISyntaxException {
        log.debug("REST request to partial update Revenu partially : {}, {}", id, revenu);
        if (revenu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, revenu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!revenuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Revenu> result = revenuService.partialUpdate(revenu);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, revenu.getId()));
    }

    /**
     * {@code GET  /revenus} : get all the revenus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of revenus in body.
     */
    @GetMapping("/revenus")
    public List<Revenu> getAllRevenus() {
        log.debug("REST request to get all Revenus");
        return revenuService.findAll();
    }

    /**
     * {@code GET  /revenus/:id} : get the "id" revenu.
     *
     * @param id the id of the revenu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the revenu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/revenus/{id}")
    public ResponseEntity<Revenu> getRevenu(@PathVariable String id) {
        log.debug("REST request to get Revenu : {}", id);
        Optional<Revenu> revenu = revenuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(revenu);
    }

    /**
     * {@code DELETE  /revenus/:id} : delete the "id" revenu.
     *
     * @param id the id of the revenu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/revenus/{id}")
    public ResponseEntity<Void> deleteRevenu(@PathVariable String id) {
        log.debug("REST request to delete Revenu : {}", id);
        revenuService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
