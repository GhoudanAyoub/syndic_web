package com.syndicg5.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.syndicg5.IntegrationTest;
import com.syndicg5.domain.Depense;
import com.syndicg5.repository.DepenseRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link DepenseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DepenseResourceIT {

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/depenses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DepenseRepository depenseRepository;

    @Autowired
    private MockMvc restDepenseMockMvc;

    private Depense depense;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Depense createEntity() {
        Depense depense = new Depense().montant(DEFAULT_MONTANT).date(DEFAULT_DATE).description(DEFAULT_DESCRIPTION);
        return depense;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Depense createUpdatedEntity() {
        Depense depense = new Depense().montant(UPDATED_MONTANT).date(UPDATED_DATE).description(UPDATED_DESCRIPTION);
        return depense;
    }

    @BeforeEach
    public void initTest() {
        depenseRepository.deleteAll();
        depense = createEntity();
    }

    @Test
    void createDepense() throws Exception {
        int databaseSizeBeforeCreate = depenseRepository.findAll().size();
        // Create the Depense
        restDepenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(depense)))
            .andExpect(status().isCreated());

        // Validate the Depense in the database
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeCreate + 1);
        Depense testDepense = depenseList.get(depenseList.size() - 1);
        assertThat(testDepense.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testDepense.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDepense.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createDepenseWithExistingId() throws Exception {
        // Create the Depense with an existing ID
        depense.setId("existing_id");

        int databaseSizeBeforeCreate = depenseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(depense)))
            .andExpect(status().isBadRequest());

        // Validate the Depense in the database
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllDepenses() throws Exception {
        // Initialize the database
        depenseRepository.save(depense);

        // Get all the depenseList
        restDepenseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depense.getId())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getDepense() throws Exception {
        // Initialize the database
        depenseRepository.save(depense);

        // Get the depense
        restDepenseMockMvc
            .perform(get(ENTITY_API_URL_ID, depense.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(depense.getId()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingDepense() throws Exception {
        // Get the depense
        restDepenseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewDepense() throws Exception {
        // Initialize the database
        depenseRepository.save(depense);

        int databaseSizeBeforeUpdate = depenseRepository.findAll().size();

        // Update the depense
        Depense updatedDepense = depenseRepository.findById(depense.getId()).get();
        updatedDepense.montant(UPDATED_MONTANT).date(UPDATED_DATE).description(UPDATED_DESCRIPTION);

        restDepenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDepense.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDepense))
            )
            .andExpect(status().isOk());

        // Validate the Depense in the database
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeUpdate);
        Depense testDepense = depenseList.get(depenseList.size() - 1);
        assertThat(testDepense.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testDepense.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDepense.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingDepense() throws Exception {
        int databaseSizeBeforeUpdate = depenseRepository.findAll().size();
        depense.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, depense.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depense))
            )
            .andExpect(status().isBadRequest());

        // Validate the Depense in the database
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDepense() throws Exception {
        int databaseSizeBeforeUpdate = depenseRepository.findAll().size();
        depense.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(depense))
            )
            .andExpect(status().isBadRequest());

        // Validate the Depense in the database
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDepense() throws Exception {
        int databaseSizeBeforeUpdate = depenseRepository.findAll().size();
        depense.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepenseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(depense)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Depense in the database
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDepenseWithPatch() throws Exception {
        // Initialize the database
        depenseRepository.save(depense);

        int databaseSizeBeforeUpdate = depenseRepository.findAll().size();

        // Update the depense using partial update
        Depense partialUpdatedDepense = new Depense();
        partialUpdatedDepense.setId(depense.getId());

        partialUpdatedDepense.montant(UPDATED_MONTANT).date(UPDATED_DATE).description(UPDATED_DESCRIPTION);

        restDepenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepense.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDepense))
            )
            .andExpect(status().isOk());

        // Validate the Depense in the database
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeUpdate);
        Depense testDepense = depenseList.get(depenseList.size() - 1);
        assertThat(testDepense.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testDepense.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDepense.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void fullUpdateDepenseWithPatch() throws Exception {
        // Initialize the database
        depenseRepository.save(depense);

        int databaseSizeBeforeUpdate = depenseRepository.findAll().size();

        // Update the depense using partial update
        Depense partialUpdatedDepense = new Depense();
        partialUpdatedDepense.setId(depense.getId());

        partialUpdatedDepense.montant(UPDATED_MONTANT).date(UPDATED_DATE).description(UPDATED_DESCRIPTION);

        restDepenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepense.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDepense))
            )
            .andExpect(status().isOk());

        // Validate the Depense in the database
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeUpdate);
        Depense testDepense = depenseList.get(depenseList.size() - 1);
        assertThat(testDepense.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testDepense.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDepense.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingDepense() throws Exception {
        int databaseSizeBeforeUpdate = depenseRepository.findAll().size();
        depense.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, depense.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(depense))
            )
            .andExpect(status().isBadRequest());

        // Validate the Depense in the database
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDepense() throws Exception {
        int databaseSizeBeforeUpdate = depenseRepository.findAll().size();
        depense.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(depense))
            )
            .andExpect(status().isBadRequest());

        // Validate the Depense in the database
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDepense() throws Exception {
        int databaseSizeBeforeUpdate = depenseRepository.findAll().size();
        depense.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepenseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(depense)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Depense in the database
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDepense() throws Exception {
        // Initialize the database
        depenseRepository.save(depense);

        int databaseSizeBeforeDelete = depenseRepository.findAll().size();

        // Delete the depense
        restDepenseMockMvc
            .perform(delete(ENTITY_API_URL_ID, depense.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
