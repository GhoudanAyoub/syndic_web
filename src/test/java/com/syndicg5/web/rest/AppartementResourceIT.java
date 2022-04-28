package com.syndicg5.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.syndicg5.IntegrationTest;
import com.syndicg5.domain.Appartement;
import com.syndicg5.repository.AppartementRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppartementRepository appartementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppartementMockMvc;

    private Appartement appartement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appartement createEntity(EntityManager em) {
        Appartement appartement = new Appartement().numero(DEFAULT_NUMERO).etage(DEFAULT_ETAGE).surface(DEFAULT_SURFACE);
        return appartement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appartement createUpdatedEntity(EntityManager em) {
        Appartement appartement = new Appartement().numero(UPDATED_NUMERO).etage(UPDATED_ETAGE).surface(UPDATED_SURFACE);
        return appartement;
    }

    @BeforeEach
    public void initTest() {
        appartement = createEntity(em);
    }

    @Test
    @Transactional
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
    @Transactional
    void createAppartementWithExistingId() throws Exception {
        // Create the Appartement with an existing ID
        appartement.setId(1L);

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
    @Transactional
    void getAllAppartements() throws Exception {
        // Initialize the database
        appartementRepository.saveAndFlush(appartement);

        // Get all the appartementList
        restAppartementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appartement.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].etage").value(hasItem(DEFAULT_ETAGE)))
            .andExpect(jsonPath("$.[*].surface").value(hasItem(DEFAULT_SURFACE.doubleValue())));
    }

    @Test
    @Transactional
    void getAppartement() throws Exception {
        // Initialize the database
        appartementRepository.saveAndFlush(appartement);

        // Get the appartement
        restAppartementMockMvc
            .perform(get(ENTITY_API_URL_ID, appartement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appartement.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.etage").value(DEFAULT_ETAGE))
            .andExpect(jsonPath("$.surface").value(DEFAULT_SURFACE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingAppartement() throws Exception {
        // Get the appartement
        restAppartementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAppartement() throws Exception {
        // Initialize the database
        appartementRepository.saveAndFlush(appartement);

        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();

        // Update the appartement
        Appartement updatedAppartement = appartementRepository.findById(appartement.getId()).get();
        // Disconnect from session so that the updates on updatedAppartement are not directly saved in db
        em.detach(updatedAppartement);
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
    @Transactional
    void putNonExistingAppartement() throws Exception {
        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();
        appartement.setId(count.incrementAndGet());

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
    @Transactional
    void putWithIdMismatchAppartement() throws Exception {
        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();
        appartement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppartementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appartement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appartement in the database
        List<Appartement> appartementList = appartementRepository.findAll();
        assertThat(appartementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppartement() throws Exception {
        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();
        appartement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppartementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appartement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Appartement in the database
        List<Appartement> appartementList = appartementRepository.findAll();
        assertThat(appartementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppartementWithPatch() throws Exception {
        // Initialize the database
        appartementRepository.saveAndFlush(appartement);

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
    @Transactional
    void fullUpdateAppartementWithPatch() throws Exception {
        // Initialize the database
        appartementRepository.saveAndFlush(appartement);

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
    @Transactional
    void patchNonExistingAppartement() throws Exception {
        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();
        appartement.setId(count.incrementAndGet());

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
    @Transactional
    void patchWithIdMismatchAppartement() throws Exception {
        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();
        appartement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppartementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appartement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Appartement in the database
        List<Appartement> appartementList = appartementRepository.findAll();
        assertThat(appartementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppartement() throws Exception {
        int databaseSizeBeforeUpdate = appartementRepository.findAll().size();
        appartement.setId(count.incrementAndGet());

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
    @Transactional
    void deleteAppartement() throws Exception {
        // Initialize the database
        appartementRepository.saveAndFlush(appartement);

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
