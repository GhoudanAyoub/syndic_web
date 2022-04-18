package com.syndicg5.web.rest;

import com.syndicg5.domain.Resident;
import com.syndicg5.repository.ResidentRepository;
import com.syndicg5.service.ResidentService;
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
 * REST controller for managing {@link com.syndicg5.domain.Resident}.
 */
@RestController
@RequestMapping("/api")
public class ResidentResource {

    private final Logger log = LoggerFactory.getLogger(ResidentResource.class);

    private static final String ENTITY_NAME = "resident";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResidentService residentService;

    private final ResidentRepository residentRepository;

    public ResidentResource(ResidentService residentService, ResidentRepository residentRepository) {
        this.residentService = residentService;
        this.residentRepository = residentRepository;
    }

    /**
     * {@code POST  /residents} : Create a new resident.
     *
     * @param resident the resident to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resident, or with status {@code 400 (Bad Request)} if the resident has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/residents")
    public ResponseEntity<Resident> createResident(@RequestBody Resident resident) throws URISyntaxException {
        log.debug("REST request to save Resident : {}", resident);
        if (resident.getId() != null) {
            throw new BadRequestAlertException("A new resident cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Resident result = residentService.save(resident);
        return ResponseEntity
            .created(new URI("/api/residents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /residents/:id} : Updates an existing resident.
     *
     * @param id the id of the resident to save.
     * @param resident the resident to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resident,
     * or with status {@code 400 (Bad Request)} if the resident is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resident couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/residents/{id}")
    public ResponseEntity<Resident> updateResident(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Resident resident
    ) throws URISyntaxException {
        log.debug("REST request to update Resident : {}, {}", id, resident);
        if (resident.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resident.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!residentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Resident result = residentService.update(resident);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resident.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /residents/:id} : Partial updates given fields of an existing resident, field will ignore if it is null
     *
     * @param id the id of the resident to save.
     * @param resident the resident to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resident,
     * or with status {@code 400 (Bad Request)} if the resident is not valid,
     * or with status {@code 404 (Not Found)} if the resident is not found,
     * or with status {@code 500 (Internal Server Error)} if the resident couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/residents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Resident> partialUpdateResident(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Resident resident
    ) throws URISyntaxException {
        log.debug("REST request to partial update Resident partially : {}, {}", id, resident);
        if (resident.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resident.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!residentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Resident> result = residentService.partialUpdate(resident);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resident.getId())
        );
    }

    /**
     * {@code GET  /residents} : get all the residents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of residents in body.
     */
    @GetMapping("/residents")
    public List<Resident> getAllResidents() {
        log.debug("REST request to get all Residents");
        return residentService.findAll();
    }

    /**
     * {@code GET  /residents/:id} : get the "id" resident.
     *
     * @param id the id of the resident to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resident, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/residents/{id}")
    public ResponseEntity<Resident> getResident(@PathVariable String id) {
        log.debug("REST request to get Resident : {}", id);
        Optional<Resident> resident = residentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resident);
    }

    /**
     * {@code DELETE  /residents/:id} : delete the "id" resident.
     *
     * @param id the id of the resident to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/residents/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable String id) {
        log.debug("REST request to delete Resident : {}", id);
        residentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
