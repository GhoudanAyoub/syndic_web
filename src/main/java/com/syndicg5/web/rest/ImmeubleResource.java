package com.syndicg5.web.rest;

import com.syndicg5.domain.Immeuble;
import com.syndicg5.repository.ImmeubleRepository;
import com.syndicg5.service.ImmeubleService;
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
 * REST controller for managing {@link com.syndicg5.domain.Immeuble}.
 */
@RestController
@RequestMapping("/api")
public class ImmeubleResource {

    private final Logger log = LoggerFactory.getLogger(ImmeubleResource.class);

    private static final String ENTITY_NAME = "immeuble";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImmeubleService immeubleService;

    private final ImmeubleRepository immeubleRepository;

    public ImmeubleResource(ImmeubleService immeubleService, ImmeubleRepository immeubleRepository) {
        this.immeubleService = immeubleService;
        this.immeubleRepository = immeubleRepository;
    }

    /**
     * {@code POST  /immeubles} : Create a new immeuble.
     *
     * @param immeuble the immeuble to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new immeuble, or with status {@code 400 (Bad Request)} if the immeuble has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/immeubles")
    public ResponseEntity<Immeuble> createImmeuble(@RequestBody Immeuble immeuble) throws URISyntaxException {
        log.debug("REST request to save Immeuble : {}", immeuble);
        if (immeuble.getId() != null) {
            throw new BadRequestAlertException("A new immeuble cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Immeuble result = immeubleService.save(immeuble);
        return ResponseEntity
            .created(new URI("/api/immeubles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /immeubles/:id} : Updates an existing immeuble.
     *
     * @param id the id of the immeuble to save.
     * @param immeuble the immeuble to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated immeuble,
     * or with status {@code 400 (Bad Request)} if the immeuble is not valid,
     * or with status {@code 500 (Internal Server Error)} if the immeuble couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/immeubles/{id}")
    public ResponseEntity<Immeuble> updateImmeuble(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Immeuble immeuble
    ) throws URISyntaxException {
        log.debug("REST request to update Immeuble : {}, {}", id, immeuble);
        if (immeuble.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, immeuble.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!immeubleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Immeuble result = immeubleService.update(immeuble);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, immeuble.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /immeubles/:id} : Partial updates given fields of an existing immeuble, field will ignore if it is null
     *
     * @param id the id of the immeuble to save.
     * @param immeuble the immeuble to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated immeuble,
     * or with status {@code 400 (Bad Request)} if the immeuble is not valid,
     * or with status {@code 404 (Not Found)} if the immeuble is not found,
     * or with status {@code 500 (Internal Server Error)} if the immeuble couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/immeubles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Immeuble> partialUpdateImmeuble(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Immeuble immeuble
    ) throws URISyntaxException {
        log.debug("REST request to partial update Immeuble partially : {}, {}", id, immeuble);
        if (immeuble.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, immeuble.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!immeubleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Immeuble> result = immeubleService.partialUpdate(immeuble);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, immeuble.getId().toString())
        );
    }

    /**
     * {@code GET  /immeubles} : get all the immeubles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of immeubles in body.
     */
    @GetMapping("/immeubles")
    public List<Immeuble> getAllImmeubles() {
        log.debug("REST request to get all Immeubles");
        return immeubleService.findAll();
    }

    /**
     * {@code GET  /immeubles/:id} : get the "id" immeuble.
     *
     * @param id the id of the immeuble to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the immeuble, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/immeubles/{id}")
    public ResponseEntity<Immeuble> getImmeuble(@PathVariable Long id) {
        log.debug("REST request to get Immeuble : {}", id);
        Optional<Immeuble> immeuble = immeubleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(immeuble);
    }

    /**
     * {@code DELETE  /immeubles/:id} : delete the "id" immeuble.
     *
     * @param id the id of the immeuble to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/immeubles/{id}")
    public ResponseEntity<Void> deleteImmeuble(@PathVariable Long id) {
        log.debug("REST request to delete Immeuble : {}", id);
        immeubleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
