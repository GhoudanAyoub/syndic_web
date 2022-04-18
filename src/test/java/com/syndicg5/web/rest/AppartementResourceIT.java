package com.syndicg5.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.syndicg5.IntegrationTest;
import com.syndicg5.domain.Appartement;
import com.syndicg5.repository.AppartementRepository;
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
 * Integration tests for the {@link AppartementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppartementResourceIT {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final Integer DEFAULT_ETAGE = 1;
    private static final Integer UPDATED_ETAGE = 2;

    private static final Double DEFAULT_SURFACE = 1D;
    private static final Double UPDATED_SURFACE = 2D;

    private static final String ENTITY_API_URL = "/api/appartements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AppartementRepository appartementRepository;

    @Autowired
    private MockMvc restAppartementMockMvc;

    private Appartement appartement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appartement createEntity() {
        Appartement appartement = new Appartement().numero(DEFAULT_NUMERO).etage(DEFAULT_ETAGE).surface(DEFAULT_SURFACE);
        return appartement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appartement createUpdatedEntity() {
        Appartement appartement = new Appartement().numero(UPDATED_NUMERO).etage(UPDATED_ETAGE).surface(UPDATED_SURFACE);
        return appartement;
    }

    @BeforeEach
    public void initTest() {
        appartementRepository.deleteAll();
        appartement = createEntity();
    }

    @Test
    void createAppartement() throws Exception {
        int databaseSizeBeforeCreate = appartementRepository.findAll().size();
        // Create the Appartement
        restAppartementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appartement)))
            .andExpect(status().isCreated());

        // Validate the Appartement in the database
        List<Appartement> appartementList = appartementRepository.findAll();
        assertThat(appartementList).hasSize(databaseSizeBeforeCreate + 1);
        Appartement testAppartement = appartementList.get(appartementList.size() - 1);
        assertThat(testAppartement.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAppartement.getEtage()).isEqualTo(DEFAULT_ETAGE);
        assertThat(testAppartement.getSurface()).isEqualTo(DEFAULT_SURFACE);
    }

    @Test
    void createAppartementWithExistingId() throws Exception {
        // Create the Appartement with an existing ID
        appartement.setId("existing_id");

        int databaseSizeBeforeCreate = appartementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppartementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appartement)))
            .andExpect(status().isBadRequest());

        // Validate the Appartement in the database
        List<Appartement> appartementList = appartementRepository.findAll();
        assertThat(appartementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllAppartements() throws Exception {
        // Initialize the database
        appartementRepository.save(appartement);

        // Get all the appartementList
        restAppartementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appartement.getId())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].etage").value(hasItem(DEFAULT_ETAGE)))
            .andExpect(jsonPath("$.[*].surface").value(hasItem(DEFAULT_SURFACE.doubleValue())));
    }

    @Test
    void getAppartement() throws Exception {
        // Initialize the database
        appartementRepository.save(appartement);

        // Get the appartement
        restAppartementMockMvc
            .perform(get(ENTITY_API_URL_ID, appartement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appartement.getId()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.etage").value(DEFAULT_ETAGE))
            .andExpect(jsonPath("$.surface").value(DEFAULT_SURFACE.doubleValue()));
    }

    @Test
    void getNonExistingAppartement() throws Exception {
        // Get the appartement
        restAppartementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewAppartement() throws Exception {
        // Initialize the database
        appartementRepository.save(appartement);

        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();

        // Update the appartement
        Appartement updatedAppartement = appartementRepository.findById(appartement.getId()).get();
        updatedAppartement.numero(UPDATED_NUMERO).etage(UPDATED_ETAGE).surface(UPDATED_SURFACE);

        restAppartementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAppartement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAppartement))
            )
            .andExpect(status().isOk());

        // Validate the Appartement in the database
        List<Appartement> appartementList = appartementRepository.findAll();
        assertThat(appartementList).hasSize(databaseSizeBeforeUpdate);
        Appartement testAppartement = appartementList.get(appartementList.size() - 1);
        assertThat(testAppartement.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAppartement.getEtage()).isEqualTo(UPDATED_ETAGE);
        assertThat(testAppartement.getSurface()).isEqualTo(UPDATED_SURFACE);
    }

    @Test
    void putNonExistingAppartement() throws Exception {
        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();
        appartement.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppartementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appartement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appartement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appartement in the database
        List<Appartement> appartementList = appartementRepository.findAll();
        assertThat(appartementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAppartement() throws Exception {
        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();
        appartement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppartementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appartement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appartement in the database
        List<Appartement> appartementList = appartementRepository.findAll();
        assertThat(appartementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAppartement() throws Exception {
        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();
        appartement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppartementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appartement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Appartement in the database
        List<Appartement> appartementList = appartementRepository.findAll();
        assertThat(appartementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAppartementWithPatch() throws Exception {
        // Initialize the database
        appartementRepository.save(appartement);

        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();

        // Update the appartement using partial update
        Appartement partialUpdatedAppartement = new Appartement();
        partialUpdatedAppartement.setId(appartement.getId());

        partialUpdatedAppartement.etage(UPDATED_ETAGE);

        restAppartementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppartement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppartement))
            )
            .andExpect(status().isOk());

        // Validate the Appartement in the database
        List<Appartement> appartementList = appartementRepository.findAll();
        assertThat(appartementList).hasSize(databaseSizeBeforeUpdate);
        Appartement testAppartement = appartementList.get(appartementList.size() - 1);
        assertThat(testAppartement.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAppartement.getEtage()).isEqualTo(UPDATED_ETAGE);
        assertThat(testAppartement.getSurface()).isEqualTo(DEFAULT_SURFACE);
    }

    @Test
    void fullUpdateAppartementWithPatch() throws Exception {
        // Initialize the database
        appartementRepository.save(appartement);

        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();

        // Update the appartement using partial update
        Appartement partialUpdatedAppartement = new Appartement();
        partialUpdatedAppartement.setId(appartement.getId());

        partialUpdatedAppartement.numero(UPDATED_NUMERO).etage(UPDATED_ETAGE).surface(UPDATED_SURFACE);

        restAppartementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppartement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppartement))
            )
            .andExpect(status().isOk());

        // Validate the Appartement in the database
        List<Appartement> appartementList = appartementRepository.findAll();
        assertThat(appartementList).hasSize(databaseSizeBeforeUpdate);
        Appartement testAppartement = appartementList.get(appartementList.size() - 1);
        assertThat(testAppartement.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAppartement.getEtage()).isEqualTo(UPDATED_ETAGE);
        assertThat(testAppartement.getSurface()).isEqualTo(UPDATED_SURFACE);
    }

    @Test
    void patchNonExistingAppartement() throws Exception {
        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();
        appartement.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppartementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appartement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appartement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appartement in the database
        List<Appartement> appartementList = appartementRepository.findAll();
        assertThat(appartementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAppartement() throws Exception {
        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();
        appartement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppartementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appartement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appartement in the database
        List<Appartement> appartementList = appartementRepository.findAll();
        assertThat(appartementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAppartement() throws Exception {
        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();
        appartement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppartementMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appartement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Appartement in the database
        List<Appartement> appartementList = appartementRepository.findAll();
        assertThat(appartementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAppartement() throws Exception {
        // Initialize the database
        appartementRepository.save(appartement);

        int databaseSizeBeforeDelete = appartementRepository.findAll().size();

        // Delete the appartement
        restAppartementMockMvc
            .perform(delete(ENTITY_API_URL_ID, appartement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Appartement> appartementList = appartementRepository.findAll();
        assertThat(appartementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
