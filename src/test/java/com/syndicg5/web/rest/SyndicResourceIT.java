package com.syndicg5.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.syndicg5.IntegrationTest;
import com.syndicg5.domain.Syndic;
import com.syndicg5.repository.SyndicRepository;
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
 * Integration tests for the {@link SyndicResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SyndicResourceIT {

    private static final String ENTITY_API_URL = "/api/syndics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SyndicRepository syndicRepository;

    @Autowired
    private MockMvc restSyndicMockMvc;

    private Syndic syndic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Syndic createEntity() {
        Syndic syndic = new Syndic();
        return syndic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Syndic createUpdatedEntity() {
        Syndic syndic = new Syndic();
        return syndic;
    }

    @BeforeEach
    public void initTest() {
        syndicRepository.deleteAll();
        syndic = createEntity();
    }

    @Test
    void createSyndic() throws Exception {
        int databaseSizeBeforeCreate = syndicRepository.findAll().size();
        // Create the Syndic
        restSyndicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(syndic)))
            .andExpect(status().isCreated());

        // Validate the Syndic in the database
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeCreate + 1);
        Syndic testSyndic = syndicList.get(syndicList.size() - 1);
    }

    @Test
    void createSyndicWithExistingId() throws Exception {
        // Create the Syndic with an existing ID
        syndic.setId("existing_id");

        int databaseSizeBeforeCreate = syndicRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSyndicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(syndic)))
            .andExpect(status().isBadRequest());

        // Validate the Syndic in the database
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSyndics() throws Exception {
        // Initialize the database
        syndicRepository.save(syndic);

        // Get all the syndicList
        restSyndicMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(syndic.getId())));
    }

    @Test
    void getSyndic() throws Exception {
        // Initialize the database
        syndicRepository.save(syndic);

        // Get the syndic
        restSyndicMockMvc
            .perform(get(ENTITY_API_URL_ID, syndic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(syndic.getId()));
    }

    @Test
    void getNonExistingSyndic() throws Exception {
        // Get the syndic
        restSyndicMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewSyndic() throws Exception {
        // Initialize the database
        syndicRepository.save(syndic);

        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();

        // Update the syndic
        Syndic updatedSyndic = syndicRepository.findById(syndic.getId()).get();

        restSyndicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSyndic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSyndic))
            )
            .andExpect(status().isOk());

        // Validate the Syndic in the database
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeUpdate);
        Syndic testSyndic = syndicList.get(syndicList.size() - 1);
    }

    @Test
    void putNonExistingSyndic() throws Exception {
        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();
        syndic.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSyndicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, syndic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(syndic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Syndic in the database
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSyndic() throws Exception {
        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();
        syndic.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSyndicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(syndic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Syndic in the database
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSyndic() throws Exception {
        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();
        syndic.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSyndicMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(syndic)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Syndic in the database
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSyndicWithPatch() throws Exception {
        // Initialize the database
        syndicRepository.save(syndic);

        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();

        // Update the syndic using partial update
        Syndic partialUpdatedSyndic = new Syndic();
        partialUpdatedSyndic.setId(syndic.getId());

        restSyndicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSyndic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSyndic))
            )
            .andExpect(status().isOk());

        // Validate the Syndic in the database
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeUpdate);
        Syndic testSyndic = syndicList.get(syndicList.size() - 1);
    }

    @Test
    void fullUpdateSyndicWithPatch() throws Exception {
        // Initialize the database
        syndicRepository.save(syndic);

        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();

        // Update the syndic using partial update
        Syndic partialUpdatedSyndic = new Syndic();
        partialUpdatedSyndic.setId(syndic.getId());

        restSyndicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSyndic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSyndic))
            )
            .andExpect(status().isOk());

        // Validate the Syndic in the database
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeUpdate);
        Syndic testSyndic = syndicList.get(syndicList.size() - 1);
    }

    @Test
    void patchNonExistingSyndic() throws Exception {
        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();
        syndic.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSyndicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, syndic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(syndic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Syndic in the database
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSyndic() throws Exception {
        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();
        syndic.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSyndicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(syndic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Syndic in the database
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSyndic() throws Exception {
        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();
        syndic.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSyndicMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(syndic)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Syndic in the database
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSyndic() throws Exception {
        // Initialize the database
        syndicRepository.save(syndic);

        int databaseSizeBeforeDelete = syndicRepository.findAll().size();

        // Delete the syndic
        restSyndicMockMvc
            .perform(delete(ENTITY_API_URL_ID, syndic.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
