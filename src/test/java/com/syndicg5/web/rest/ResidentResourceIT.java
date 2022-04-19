package com.syndicg5.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.syndicg5.IntegrationTest;
import com.syndicg5.domain.Resident;
import com.syndicg5.repository.ResidentRepository;
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
 * Integration tests for the {@link ResidentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResidentResourceIT {

    private static final String DEFAULT_ETAT_FAMILIALE = "AAAAAAAAAA";
    private static final String UPDATED_ETAT_FAMILIALE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/residents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private MockMvc restResidentMockMvc;

    private Resident resident;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resident createEntity() {
        Resident resident = new Resident().etatFamiliale(DEFAULT_ETAT_FAMILIALE);
        return resident;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resident createUpdatedEntity() {
        Resident resident = new Resident().etatFamiliale(UPDATED_ETAT_FAMILIALE);
        return resident;
    }

    @BeforeEach
    public void initTest() {
        residentRepository.deleteAll();
        resident = createEntity();
    }

    @Test
    void createResident() throws Exception {
        int databaseSizeBeforeCreate = residentRepository.findAll().size();
        // Create the Resident
        restResidentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resident)))
            .andExpect(status().isCreated());

        // Validate the Resident in the database
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeCreate + 1);
        Resident testResident = residentList.get(residentList.size() - 1);
        assertThat(testResident.getEtatFamiliale()).isEqualTo(DEFAULT_ETAT_FAMILIALE);
    }

    @Test
    void createResidentWithExistingId() throws Exception {
        // Create the Resident with an existing ID
        resident.setId("existing_id");

        int databaseSizeBeforeCreate = residentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResidentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resident)))
            .andExpect(status().isBadRequest());

        // Validate the Resident in the database
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllResidents() throws Exception {
        // Initialize the database
        residentRepository.save(resident);

        // Get all the residentList
        restResidentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resident.getId())))
            .andExpect(jsonPath("$.[*].etatFamiliale").value(hasItem(DEFAULT_ETAT_FAMILIALE)));
    }

    @Test
    void getResident() throws Exception {
        // Initialize the database
        residentRepository.save(resident);

        // Get the resident
        restResidentMockMvc
            .perform(get(ENTITY_API_URL_ID, resident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resident.getId()))
            .andExpect(jsonPath("$.etatFamiliale").value(DEFAULT_ETAT_FAMILIALE));
    }

    @Test
    void getNonExistingResident() throws Exception {
        // Get the resident
        restResidentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewResident() throws Exception {
        // Initialize the database
        residentRepository.save(resident);

        int databaseSizeBeforeUpdate = residentRepository.findAll().size();

        // Update the resident
        Resident updatedResident = residentRepository.findById(resident.getId()).get();
        updatedResident.etatFamiliale(UPDATED_ETAT_FAMILIALE);

        restResidentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResident.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedResident))
            )
            .andExpect(status().isOk());

        // Validate the Resident in the database
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeUpdate);
        Resident testResident = residentList.get(residentList.size() - 1);
        assertThat(testResident.getEtatFamiliale()).isEqualTo(UPDATED_ETAT_FAMILIALE);
    }

    @Test
    void putNonExistingResident() throws Exception {
        int databaseSizeBeforeUpdate = residentRepository.findAll().size();
        resident.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResidentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resident.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resident in the database
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchResident() throws Exception {
        int databaseSizeBeforeUpdate = residentRepository.findAll().size();
        resident.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResidentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resident in the database
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamResident() throws Exception {
        int databaseSizeBeforeUpdate = residentRepository.findAll().size();
        resident.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResidentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resident)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Resident in the database
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateResidentWithPatch() throws Exception {
        // Initialize the database
        residentRepository.save(resident);

        int databaseSizeBeforeUpdate = residentRepository.findAll().size();

        // Update the resident using partial update
        Resident partialUpdatedResident = new Resident();
        partialUpdatedResident.setId(resident.getId());

        restResidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResident.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResident))
            )
            .andExpect(status().isOk());

        // Validate the Resident in the database
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeUpdate);
        Resident testResident = residentList.get(residentList.size() - 1);
        assertThat(testResident.getEtatFamiliale()).isEqualTo(DEFAULT_ETAT_FAMILIALE);
    }

    @Test
    void fullUpdateResidentWithPatch() throws Exception {
        // Initialize the database
        residentRepository.save(resident);

        int databaseSizeBeforeUpdate = residentRepository.findAll().size();

        // Update the resident using partial update
        Resident partialUpdatedResident = new Resident();
        partialUpdatedResident.setId(resident.getId());

        partialUpdatedResident.etatFamiliale(UPDATED_ETAT_FAMILIALE);

        restResidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResident.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResident))
            )
            .andExpect(status().isOk());

        // Validate the Resident in the database
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeUpdate);
        Resident testResident = residentList.get(residentList.size() - 1);
        assertThat(testResident.getEtatFamiliale()).isEqualTo(UPDATED_ETAT_FAMILIALE);
    }

    @Test
    void patchNonExistingResident() throws Exception {
        int databaseSizeBeforeUpdate = residentRepository.findAll().size();
        resident.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resident.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resident in the database
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchResident() throws Exception {
        int databaseSizeBeforeUpdate = residentRepository.findAll().size();
        resident.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resident in the database
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamResident() throws Exception {
        int databaseSizeBeforeUpdate = residentRepository.findAll().size();
        resident.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResidentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(resident)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Resident in the database
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteResident() throws Exception {
        // Initialize the database
        residentRepository.save(resident);

        int databaseSizeBeforeDelete = residentRepository.findAll().size();

        // Delete the resident
        restResidentMockMvc
            .perform(delete(ENTITY_API_URL_ID, resident.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Resident> residentList = residentRepository.findAll();
        assertThat(residentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
