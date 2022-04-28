package com.syndicg5.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.syndicg5.IntegrationTest;
import com.syndicg5.domain.Syndic;
import com.syndicg5.repository.SyndicRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link SyndicResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SyndicResourceIT {

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_TRAVAIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TRAVAIL = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/syndics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SyndicRepository syndicRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSyndicMockMvc;

    private Syndic syndic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Syndic createEntity(EntityManager em) {
        Syndic syndic = new Syndic()
            .adresse(DEFAULT_ADRESSE)
            .tel(DEFAULT_TEL)
            .dateTravail(DEFAULT_DATE_TRAVAIL)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return syndic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Syndic createUpdatedEntity(EntityManager em) {
        Syndic syndic = new Syndic()
            .adresse(UPDATED_ADRESSE)
            .tel(UPDATED_TEL)
            .dateTravail(UPDATED_DATE_TRAVAIL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        return syndic;
    }

    @BeforeEach
    public void initTest() {
        syndic = createEntity(em);
    }

    @Test
    @Transactional
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
        assertThat(testSyndic.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testSyndic.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testSyndic.getDateTravail()).isEqualTo(DEFAULT_DATE_TRAVAIL);
        assertThat(testSyndic.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testSyndic.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createSyndicWithExistingId() throws Exception {
        // Create the Syndic with an existing ID
        syndic.setId(1L);

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
    @Transactional
    void getAllSyndics() throws Exception {
        // Initialize the database
        syndicRepository.saveAndFlush(syndic);

        // Get all the syndicList
        restSyndicMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(syndic.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].dateTravail").value(hasItem(DEFAULT_DATE_TRAVAIL.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    void getSyndic() throws Exception {
        // Initialize the database
        syndicRepository.saveAndFlush(syndic);

        // Get the syndic
        restSyndicMockMvc
            .perform(get(ENTITY_API_URL_ID, syndic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(syndic.getId().intValue()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL))
            .andExpect(jsonPath("$.dateTravail").value(DEFAULT_DATE_TRAVAIL.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    void getNonExistingSyndic() throws Exception {
        // Get the syndic
        restSyndicMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSyndic() throws Exception {
        // Initialize the database
        syndicRepository.saveAndFlush(syndic);

        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();

        // Update the syndic
        Syndic updatedSyndic = syndicRepository.findById(syndic.getId()).get();
        // Disconnect from session so that the updates on updatedSyndic are not directly saved in db
        em.detach(updatedSyndic);
        updatedSyndic
            .adresse(UPDATED_ADRESSE)
            .tel(UPDATED_TEL)
            .dateTravail(UPDATED_DATE_TRAVAIL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

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
        assertThat(testSyndic.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testSyndic.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testSyndic.getDateTravail()).isEqualTo(UPDATED_DATE_TRAVAIL);
        assertThat(testSyndic.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testSyndic.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingSyndic() throws Exception {
        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();
        syndic.setId(count.incrementAndGet());

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
    @Transactional
    void putWithIdMismatchSyndic() throws Exception {
        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();
        syndic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSyndicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(syndic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Syndic in the database
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSyndic() throws Exception {
        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();
        syndic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSyndicMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(syndic)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Syndic in the database
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSyndicWithPatch() throws Exception {
        // Initialize the database
        syndicRepository.saveAndFlush(syndic);

        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();

        // Update the syndic using partial update
        Syndic partialUpdatedSyndic = new Syndic();
        partialUpdatedSyndic.setId(syndic.getId());

        partialUpdatedSyndic
            .tel(UPDATED_TEL)
            .dateTravail(UPDATED_DATE_TRAVAIL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

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
        assertThat(testSyndic.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testSyndic.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testSyndic.getDateTravail()).isEqualTo(UPDATED_DATE_TRAVAIL);
        assertThat(testSyndic.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testSyndic.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateSyndicWithPatch() throws Exception {
        // Initialize the database
        syndicRepository.saveAndFlush(syndic);

        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();

        // Update the syndic using partial update
        Syndic partialUpdatedSyndic = new Syndic();
        partialUpdatedSyndic.setId(syndic.getId());

        partialUpdatedSyndic
            .adresse(UPDATED_ADRESSE)
            .tel(UPDATED_TEL)
            .dateTravail(UPDATED_DATE_TRAVAIL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

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
        assertThat(testSyndic.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testSyndic.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testSyndic.getDateTravail()).isEqualTo(UPDATED_DATE_TRAVAIL);
        assertThat(testSyndic.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testSyndic.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingSyndic() throws Exception {
        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();
        syndic.setId(count.incrementAndGet());

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
    @Transactional
    void patchWithIdMismatchSyndic() throws Exception {
        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();
        syndic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSyndicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(syndic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Syndic in the database
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSyndic() throws Exception {
        int databaseSizeBeforeUpdate = syndicRepository.findAll().size();
        syndic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSyndicMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(syndic)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Syndic in the database
        List<Syndic> syndicList = syndicRepository.findAll();
        assertThat(syndicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSyndic() throws Exception {
        // Initialize the database
        syndicRepository.saveAndFlush(syndic);

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
