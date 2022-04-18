package com.syndicg5.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.syndicg5.IntegrationTest;
import com.syndicg5.domain.Revenu;
import com.syndicg5.repository.RevenuRepository;
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
 * Integration tests for the {@link RevenuResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RevenuResourceIT {

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/revenus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private RevenuRepository revenuRepository;

    @Autowired
    private MockMvc restRevenuMockMvc;

    private Revenu revenu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Revenu createEntity() {
        Revenu revenu = new Revenu().montant(DEFAULT_MONTANT).date(DEFAULT_DATE).description(DEFAULT_DESCRIPTION);
        return revenu;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Revenu createUpdatedEntity() {
        Revenu revenu = new Revenu().montant(UPDATED_MONTANT).date(UPDATED_DATE).description(UPDATED_DESCRIPTION);
        return revenu;
    }

    @BeforeEach
    public void initTest() {
        revenuRepository.deleteAll();
        revenu = createEntity();
    }

    @Test
    void createRevenu() throws Exception {
        int databaseSizeBeforeCreate = revenuRepository.findAll().size();
        // Create the Revenu
        restRevenuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(revenu)))
            .andExpect(status().isCreated());

        // Validate the Revenu in the database
        List<Revenu> revenuList = revenuRepository.findAll();
        assertThat(revenuList).hasSize(databaseSizeBeforeCreate + 1);
        Revenu testRevenu = revenuList.get(revenuList.size() - 1);
        assertThat(testRevenu.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testRevenu.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testRevenu.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createRevenuWithExistingId() throws Exception {
        // Create the Revenu with an existing ID
        revenu.setId("existing_id");

        int databaseSizeBeforeCreate = revenuRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRevenuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(revenu)))
            .andExpect(status().isBadRequest());

        // Validate the Revenu in the database
        List<Revenu> revenuList = revenuRepository.findAll();
        assertThat(revenuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRevenus() throws Exception {
        // Initialize the database
        revenuRepository.save(revenu);

        // Get all the revenuList
        restRevenuMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(revenu.getId())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getRevenu() throws Exception {
        // Initialize the database
        revenuRepository.save(revenu);

        // Get the revenu
        restRevenuMockMvc
            .perform(get(ENTITY_API_URL_ID, revenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(revenu.getId()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingRevenu() throws Exception {
        // Get the revenu
        restRevenuMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewRevenu() throws Exception {
        // Initialize the database
        revenuRepository.save(revenu);

        int databaseSizeBeforeUpdate = revenuRepository.findAll().size();

        // Update the revenu
        Revenu updatedRevenu = revenuRepository.findById(revenu.getId()).get();
        updatedRevenu.montant(UPDATED_MONTANT).date(UPDATED_DATE).description(UPDATED_DESCRIPTION);

        restRevenuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRevenu.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRevenu))
            )
            .andExpect(status().isOk());

        // Validate the Revenu in the database
        List<Revenu> revenuList = revenuRepository.findAll();
        assertThat(revenuList).hasSize(databaseSizeBeforeUpdate);
        Revenu testRevenu = revenuList.get(revenuList.size() - 1);
        assertThat(testRevenu.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testRevenu.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRevenu.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingRevenu() throws Exception {
        int databaseSizeBeforeUpdate = revenuRepository.findAll().size();
        revenu.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRevenuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, revenu.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(revenu))
            )
            .andExpect(status().isBadRequest());

        // Validate the Revenu in the database
        List<Revenu> revenuList = revenuRepository.findAll();
        assertThat(revenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRevenu() throws Exception {
        int databaseSizeBeforeUpdate = revenuRepository.findAll().size();
        revenu.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevenuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(revenu))
            )
            .andExpect(status().isBadRequest());

        // Validate the Revenu in the database
        List<Revenu> revenuList = revenuRepository.findAll();
        assertThat(revenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRevenu() throws Exception {
        int databaseSizeBeforeUpdate = revenuRepository.findAll().size();
        revenu.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevenuMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(revenu)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Revenu in the database
        List<Revenu> revenuList = revenuRepository.findAll();
        assertThat(revenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRevenuWithPatch() throws Exception {
        // Initialize the database
        revenuRepository.save(revenu);

        int databaseSizeBeforeUpdate = revenuRepository.findAll().size();

        // Update the revenu using partial update
        Revenu partialUpdatedRevenu = new Revenu();
        partialUpdatedRevenu.setId(revenu.getId());

        partialUpdatedRevenu.montant(UPDATED_MONTANT);

        restRevenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRevenu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRevenu))
            )
            .andExpect(status().isOk());

        // Validate the Revenu in the database
        List<Revenu> revenuList = revenuRepository.findAll();
        assertThat(revenuList).hasSize(databaseSizeBeforeUpdate);
        Revenu testRevenu = revenuList.get(revenuList.size() - 1);
        assertThat(testRevenu.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testRevenu.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testRevenu.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void fullUpdateRevenuWithPatch() throws Exception {
        // Initialize the database
        revenuRepository.save(revenu);

        int databaseSizeBeforeUpdate = revenuRepository.findAll().size();

        // Update the revenu using partial update
        Revenu partialUpdatedRevenu = new Revenu();
        partialUpdatedRevenu.setId(revenu.getId());

        partialUpdatedRevenu.montant(UPDATED_MONTANT).date(UPDATED_DATE).description(UPDATED_DESCRIPTION);

        restRevenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRevenu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRevenu))
            )
            .andExpect(status().isOk());

        // Validate the Revenu in the database
        List<Revenu> revenuList = revenuRepository.findAll();
        assertThat(revenuList).hasSize(databaseSizeBeforeUpdate);
        Revenu testRevenu = revenuList.get(revenuList.size() - 1);
        assertThat(testRevenu.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testRevenu.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRevenu.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingRevenu() throws Exception {
        int databaseSizeBeforeUpdate = revenuRepository.findAll().size();
        revenu.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRevenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, revenu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(revenu))
            )
            .andExpect(status().isBadRequest());

        // Validate the Revenu in the database
        List<Revenu> revenuList = revenuRepository.findAll();
        assertThat(revenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRevenu() throws Exception {
        int databaseSizeBeforeUpdate = revenuRepository.findAll().size();
        revenu.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevenuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(revenu))
            )
            .andExpect(status().isBadRequest());

        // Validate the Revenu in the database
        List<Revenu> revenuList = revenuRepository.findAll();
        assertThat(revenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRevenu() throws Exception {
        int databaseSizeBeforeUpdate = revenuRepository.findAll().size();
        revenu.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevenuMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(revenu)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Revenu in the database
        List<Revenu> revenuList = revenuRepository.findAll();
        assertThat(revenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRevenu() throws Exception {
        // Initialize the database
        revenuRepository.save(revenu);

        int databaseSizeBeforeDelete = revenuRepository.findAll().size();

        // Delete the revenu
        restRevenuMockMvc
            .perform(delete(ENTITY_API_URL_ID, revenu.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Revenu> revenuList = revenuRepository.findAll();
        assertThat(revenuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
