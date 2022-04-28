package com.syndicg5.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.syndicg5.IntegrationTest;
import com.syndicg5.domain.Immeuble;
import com.syndicg5.repository.ImmeubleRepository;
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
 * Integration tests for the {@link ImmeubleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ImmeubleResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final Integer DEFAULT_NB_ETAGES = 1;
    private static final Integer UPDATED_NB_ETAGES = 2;

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/immeubles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ImmeubleRepository immeubleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImmeubleMockMvc;

    private Immeuble immeuble;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Immeuble createEntity(EntityManager em) {
        Immeuble immeuble = new Immeuble()
            .libelle(DEFAULT_LIBELLE)
            .adresse(DEFAULT_ADRESSE)
            .ville(DEFAULT_VILLE)
            .numero(DEFAULT_NUMERO)
            .nbEtages(DEFAULT_NB_ETAGES)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return immeuble;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Immeuble createUpdatedEntity(EntityManager em) {
        Immeuble immeuble = new Immeuble()
            .libelle(UPDATED_LIBELLE)
            .adresse(UPDATED_ADRESSE)
            .ville(UPDATED_VILLE)
            .numero(UPDATED_NUMERO)
            .nbEtages(UPDATED_NB_ETAGES)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        return immeuble;
    }

    @BeforeEach
    public void initTest() {
        immeuble = createEntity(em);
    }

    @Test
    @Transactional
    void createImmeuble() throws Exception {
        int databaseSizeBeforeCreate = immeubleRepository.findAll().size();
        // Create the Immeuble
        restImmeubleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(immeuble)))
            .andExpect(status().isCreated());

        // Validate the Immeuble in the database
        List<Immeuble> immeubleList = immeubleRepository.findAll();
        assertThat(immeubleList).hasSize(databaseSizeBeforeCreate + 1);
        Immeuble testImmeuble = immeubleList.get(immeubleList.size() - 1);
        assertThat(testImmeuble.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testImmeuble.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testImmeuble.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testImmeuble.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testImmeuble.getNbEtages()).isEqualTo(DEFAULT_NB_ETAGES);
        assertThat(testImmeuble.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testImmeuble.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createImmeubleWithExistingId() throws Exception {
        // Create the Immeuble with an existing ID
        immeuble.setId(1L);

        int databaseSizeBeforeCreate = immeubleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImmeubleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(immeuble)))
            .andExpect(status().isBadRequest());

        // Validate the Immeuble in the database
        List<Immeuble> immeubleList = immeubleRepository.findAll();
        assertThat(immeubleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllImmeubles() throws Exception {
        // Initialize the database
        immeubleRepository.saveAndFlush(immeuble);

        // Get all the immeubleList
        restImmeubleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(immeuble.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].nbEtages").value(hasItem(DEFAULT_NB_ETAGES)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    void getImmeuble() throws Exception {
        // Initialize the database
        immeubleRepository.saveAndFlush(immeuble);

        // Get the immeuble
        restImmeubleMockMvc
            .perform(get(ENTITY_API_URL_ID, immeuble.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(immeuble.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.nbEtages").value(DEFAULT_NB_ETAGES))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    void getNonExistingImmeuble() throws Exception {
        // Get the immeuble
        restImmeubleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewImmeuble() throws Exception {
        // Initialize the database
        immeubleRepository.saveAndFlush(immeuble);

        int databaseSizeBeforeUpdate = immeubleRepository.findAll().size();

        // Update the immeuble
        Immeuble updatedImmeuble = immeubleRepository.findById(immeuble.getId()).get();
        // Disconnect from session so that the updates on updatedImmeuble are not directly saved in db
        em.detach(updatedImmeuble);
        updatedImmeuble
            .libelle(UPDATED_LIBELLE)
            .adresse(UPDATED_ADRESSE)
            .ville(UPDATED_VILLE)
            .numero(UPDATED_NUMERO)
            .nbEtages(UPDATED_NB_ETAGES)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restImmeubleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedImmeuble.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedImmeuble))
            )
            .andExpect(status().isOk());

        // Validate the Immeuble in the database
        List<Immeuble> immeubleList = immeubleRepository.findAll();
        assertThat(immeubleList).hasSize(databaseSizeBeforeUpdate);
        Immeuble testImmeuble = immeubleList.get(immeubleList.size() - 1);
        assertThat(testImmeuble.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testImmeuble.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testImmeuble.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testImmeuble.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testImmeuble.getNbEtages()).isEqualTo(UPDATED_NB_ETAGES);
        assertThat(testImmeuble.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testImmeuble.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingImmeuble() throws Exception {
        int databaseSizeBeforeUpdate = immeubleRepository.findAll().size();
        immeuble.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImmeubleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, immeuble.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(immeuble))
            )
            .andExpect(status().isBadRequest());

        // Validate the Immeuble in the database
        List<Immeuble> immeubleList = immeubleRepository.findAll();
        assertThat(immeubleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchImmeuble() throws Exception {
        int databaseSizeBeforeUpdate = immeubleRepository.findAll().size();
        immeuble.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImmeubleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(immeuble))
            )
            .andExpect(status().isBadRequest());

        // Validate the Immeuble in the database
        List<Immeuble> immeubleList = immeubleRepository.findAll();
        assertThat(immeubleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamImmeuble() throws Exception {
        int databaseSizeBeforeUpdate = immeubleRepository.findAll().size();
        immeuble.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImmeubleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(immeuble)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Immeuble in the database
        List<Immeuble> immeubleList = immeubleRepository.findAll();
        assertThat(immeubleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateImmeubleWithPatch() throws Exception {
        // Initialize the database
        immeubleRepository.saveAndFlush(immeuble);

        int databaseSizeBeforeUpdate = immeubleRepository.findAll().size();

        // Update the immeuble using partial update
        Immeuble partialUpdatedImmeuble = new Immeuble();
        partialUpdatedImmeuble.setId(immeuble.getId());

        partialUpdatedImmeuble
            .adresse(UPDATED_ADRESSE)
            .nbEtages(UPDATED_NB_ETAGES)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restImmeubleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImmeuble.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImmeuble))
            )
            .andExpect(status().isOk());

        // Validate the Immeuble in the database
        List<Immeuble> immeubleList = immeubleRepository.findAll();
        assertThat(immeubleList).hasSize(databaseSizeBeforeUpdate);
        Immeuble testImmeuble = immeubleList.get(immeubleList.size() - 1);
        assertThat(testImmeuble.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testImmeuble.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testImmeuble.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testImmeuble.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testImmeuble.getNbEtages()).isEqualTo(UPDATED_NB_ETAGES);
        assertThat(testImmeuble.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testImmeuble.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateImmeubleWithPatch() throws Exception {
        // Initialize the database
        immeubleRepository.saveAndFlush(immeuble);

        int databaseSizeBeforeUpdate = immeubleRepository.findAll().size();

        // Update the immeuble using partial update
        Immeuble partialUpdatedImmeuble = new Immeuble();
        partialUpdatedImmeuble.setId(immeuble.getId());

        partialUpdatedImmeuble
            .libelle(UPDATED_LIBELLE)
            .adresse(UPDATED_ADRESSE)
            .ville(UPDATED_VILLE)
            .numero(UPDATED_NUMERO)
            .nbEtages(UPDATED_NB_ETAGES)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restImmeubleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImmeuble.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImmeuble))
            )
            .andExpect(status().isOk());

        // Validate the Immeuble in the database
        List<Immeuble> immeubleList = immeubleRepository.findAll();
        assertThat(immeubleList).hasSize(databaseSizeBeforeUpdate);
        Immeuble testImmeuble = immeubleList.get(immeubleList.size() - 1);
        assertThat(testImmeuble.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testImmeuble.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testImmeuble.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testImmeuble.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testImmeuble.getNbEtages()).isEqualTo(UPDATED_NB_ETAGES);
        assertThat(testImmeuble.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testImmeuble.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingImmeuble() throws Exception {
        int databaseSizeBeforeUpdate = immeubleRepository.findAll().size();
        immeuble.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImmeubleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, immeuble.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(immeuble))
            )
            .andExpect(status().isBadRequest());

        // Validate the Immeuble in the database
        List<Immeuble> immeubleList = immeubleRepository.findAll();
        assertThat(immeubleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchImmeuble() throws Exception {
        int databaseSizeBeforeUpdate = immeubleRepository.findAll().size();
        immeuble.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImmeubleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(immeuble))
            )
            .andExpect(status().isBadRequest());

        // Validate the Immeuble in the database
        List<Immeuble> immeubleList = immeubleRepository.findAll();
        assertThat(immeubleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamImmeuble() throws Exception {
        int databaseSizeBeforeUpdate = immeubleRepository.findAll().size();
        immeuble.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImmeubleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(immeuble)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Immeuble in the database
        List<Immeuble> immeubleList = immeubleRepository.findAll();
        assertThat(immeubleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteImmeuble() throws Exception {
        // Initialize the database
        immeubleRepository.saveAndFlush(immeuble);

        int databaseSizeBeforeDelete = immeubleRepository.findAll().size();

        // Delete the immeuble
        restImmeubleMockMvc
            .perform(delete(ENTITY_API_URL_ID, immeuble.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Immeuble> immeubleList = immeubleRepository.findAll();
        assertThat(immeubleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
