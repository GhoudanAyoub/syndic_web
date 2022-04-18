package com.syndicg5.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.syndicg5.IntegrationTest;
import com.syndicg5.domain.Payement;
import com.syndicg5.repository.PayementRepository;
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
 * Integration tests for the {@link PayementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PayementResourceIT {

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PayementRepository payementRepository;

    @Autowired
    private MockMvc restPayementMockMvc;

    private Payement payement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payement createEntity() {
        Payement payement = new Payement().montant(DEFAULT_MONTANT).date(DEFAULT_DATE).description(DEFAULT_DESCRIPTION);
        return payement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payement createUpdatedEntity() {
        Payement payement = new Payement().montant(UPDATED_MONTANT).date(UPDATED_DATE).description(UPDATED_DESCRIPTION);
        return payement;
    }

    @BeforeEach
    public void initTest() {
        payementRepository.deleteAll();
        payement = createEntity();
    }

    @Test
    void createPayement() throws Exception {
        int databaseSizeBeforeCreate = payementRepository.findAll().size();
        // Create the Payement
        restPayementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payement)))
            .andExpect(status().isCreated());

        // Validate the Payement in the database
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeCreate + 1);
        Payement testPayement = payementList.get(payementList.size() - 1);
        assertThat(testPayement.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testPayement.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPayement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createPayementWithExistingId() throws Exception {
        // Create the Payement with an existing ID
        payement.setId("existing_id");

        int databaseSizeBeforeCreate = payementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payement)))
            .andExpect(status().isBadRequest());

        // Validate the Payement in the database
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPayements() throws Exception {
        // Initialize the database
        payementRepository.save(payement);

        // Get all the payementList
        restPayementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payement.getId())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getPayement() throws Exception {
        // Initialize the database
        payementRepository.save(payement);

        // Get the payement
        restPayementMockMvc
            .perform(get(ENTITY_API_URL_ID, payement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payement.getId()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingPayement() throws Exception {
        // Get the payement
        restPayementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewPayement() throws Exception {
        // Initialize the database
        payementRepository.save(payement);

        int databaseSizeBeforeUpdate = payementRepository.findAll().size();

        // Update the payement
        Payement updatedPayement = payementRepository.findById(payement.getId()).get();
        updatedPayement.montant(UPDATED_MONTANT).date(UPDATED_DATE).description(UPDATED_DESCRIPTION);

        restPayementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPayement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPayement))
            )
            .andExpect(status().isOk());

        // Validate the Payement in the database
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeUpdate);
        Payement testPayement = payementList.get(payementList.size() - 1);
        assertThat(testPayement.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testPayement.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPayement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingPayement() throws Exception {
        int databaseSizeBeforeUpdate = payementRepository.findAll().size();
        payement.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payement in the database
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPayement() throws Exception {
        int databaseSizeBeforeUpdate = payementRepository.findAll().size();
        payement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payement in the database
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPayement() throws Exception {
        int databaseSizeBeforeUpdate = payementRepository.findAll().size();
        payement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payement in the database
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePayementWithPatch() throws Exception {
        // Initialize the database
        payementRepository.save(payement);

        int databaseSizeBeforeUpdate = payementRepository.findAll().size();

        // Update the payement using partial update
        Payement partialUpdatedPayement = new Payement();
        partialUpdatedPayement.setId(payement.getId());

        restPayementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayement))
            )
            .andExpect(status().isOk());

        // Validate the Payement in the database
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeUpdate);
        Payement testPayement = payementList.get(payementList.size() - 1);
        assertThat(testPayement.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testPayement.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPayement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void fullUpdatePayementWithPatch() throws Exception {
        // Initialize the database
        payementRepository.save(payement);

        int databaseSizeBeforeUpdate = payementRepository.findAll().size();

        // Update the payement using partial update
        Payement partialUpdatedPayement = new Payement();
        partialUpdatedPayement.setId(payement.getId());

        partialUpdatedPayement.montant(UPDATED_MONTANT).date(UPDATED_DATE).description(UPDATED_DESCRIPTION);

        restPayementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayement))
            )
            .andExpect(status().isOk());

        // Validate the Payement in the database
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeUpdate);
        Payement testPayement = payementList.get(payementList.size() - 1);
        assertThat(testPayement.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testPayement.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPayement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingPayement() throws Exception {
        int databaseSizeBeforeUpdate = payementRepository.findAll().size();
        payement.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payement in the database
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPayement() throws Exception {
        int databaseSizeBeforeUpdate = payementRepository.findAll().size();
        payement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payement in the database
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPayement() throws Exception {
        int databaseSizeBeforeUpdate = payementRepository.findAll().size();
        payement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayementMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(payement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payement in the database
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePayement() throws Exception {
        // Initialize the database
        payementRepository.save(payement);

        int databaseSizeBeforeDelete = payementRepository.findAll().size();

        // Delete the payement
        restPayementMockMvc
            .perform(delete(ENTITY_API_URL_ID, payement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
