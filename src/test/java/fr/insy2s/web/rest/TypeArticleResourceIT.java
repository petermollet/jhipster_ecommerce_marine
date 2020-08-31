package fr.insy2s.web.rest;

import fr.insy2s.ECommerceJhipsterV1App;
import fr.insy2s.domain.TypeArticle;
import fr.insy2s.repository.TypeArticleRepository;
import fr.insy2s.service.TypeArticleService;
import fr.insy2s.service.dto.TypeArticleDTO;
import fr.insy2s.service.mapper.TypeArticleMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TypeArticleResource} REST controller.
 */
@SpringBootTest(classes = ECommerceJhipsterV1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class TypeArticleResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypeArticleRepository typeArticleRepository;

    @Autowired
    private TypeArticleMapper typeArticleMapper;

    @Autowired
    private TypeArticleService typeArticleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeArticleMockMvc;

    private TypeArticle typeArticle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeArticle createEntity(EntityManager em) {
        TypeArticle typeArticle = new TypeArticle()
            .libelle(DEFAULT_LIBELLE);
        return typeArticle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeArticle createUpdatedEntity(EntityManager em) {
        TypeArticle typeArticle = new TypeArticle()
            .libelle(UPDATED_LIBELLE);
        return typeArticle;
    }

    @BeforeEach
    public void initTest() {
        typeArticle = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeArticle() throws Exception {
        int databaseSizeBeforeCreate = typeArticleRepository.findAll().size();

        // Create the TypeArticle
        TypeArticleDTO typeArticleDTO = typeArticleMapper.toDto(typeArticle);
        restTypeArticleMockMvc.perform(post("/api/type-articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeArticleDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeArticle in the database
        List<TypeArticle> typeArticleList = typeArticleRepository.findAll();
        assertThat(typeArticleList).hasSize(databaseSizeBeforeCreate + 1);
        TypeArticle testTypeArticle = typeArticleList.get(typeArticleList.size() - 1);
        assertThat(testTypeArticle.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createTypeArticleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeArticleRepository.findAll().size();

        // Create the TypeArticle with an existing ID
        typeArticle.setId(1L);
        TypeArticleDTO typeArticleDTO = typeArticleMapper.toDto(typeArticle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeArticleMockMvc.perform(post("/api/type-articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeArticleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeArticle in the database
        List<TypeArticle> typeArticleList = typeArticleRepository.findAll();
        assertThat(typeArticleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeArticleRepository.findAll().size();
        // set the field null
        typeArticle.setLibelle(null);

        // Create the TypeArticle, which fails.
        TypeArticleDTO typeArticleDTO = typeArticleMapper.toDto(typeArticle);

        restTypeArticleMockMvc.perform(post("/api/type-articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeArticleDTO)))
            .andExpect(status().isBadRequest());

        List<TypeArticle> typeArticleList = typeArticleRepository.findAll();
        assertThat(typeArticleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeArticles() throws Exception {
        // Initialize the database
        typeArticleRepository.saveAndFlush(typeArticle);

        // Get all the typeArticleList
        restTypeArticleMockMvc.perform(get("/api/type-articles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeArticle.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getTypeArticle() throws Exception {
        // Initialize the database
        typeArticleRepository.saveAndFlush(typeArticle);

        // Get the typeArticle
        restTypeArticleMockMvc.perform(get("/api/type-articles/{id}", typeArticle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeArticle.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    public void getNonExistingTypeArticle() throws Exception {
        // Get the typeArticle
        restTypeArticleMockMvc.perform(get("/api/type-articles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeArticle() throws Exception {
        // Initialize the database
        typeArticleRepository.saveAndFlush(typeArticle);

        int databaseSizeBeforeUpdate = typeArticleRepository.findAll().size();

        // Update the typeArticle
        TypeArticle updatedTypeArticle = typeArticleRepository.findById(typeArticle.getId()).get();
        // Disconnect from session so that the updates on updatedTypeArticle are not directly saved in db
        em.detach(updatedTypeArticle);
        updatedTypeArticle
            .libelle(UPDATED_LIBELLE);
        TypeArticleDTO typeArticleDTO = typeArticleMapper.toDto(updatedTypeArticle);

        restTypeArticleMockMvc.perform(put("/api/type-articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeArticleDTO)))
            .andExpect(status().isOk());

        // Validate the TypeArticle in the database
        List<TypeArticle> typeArticleList = typeArticleRepository.findAll();
        assertThat(typeArticleList).hasSize(databaseSizeBeforeUpdate);
        TypeArticle testTypeArticle = typeArticleList.get(typeArticleList.size() - 1);
        assertThat(testTypeArticle.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeArticle() throws Exception {
        int databaseSizeBeforeUpdate = typeArticleRepository.findAll().size();

        // Create the TypeArticle
        TypeArticleDTO typeArticleDTO = typeArticleMapper.toDto(typeArticle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeArticleMockMvc.perform(put("/api/type-articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeArticleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeArticle in the database
        List<TypeArticle> typeArticleList = typeArticleRepository.findAll();
        assertThat(typeArticleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeArticle() throws Exception {
        // Initialize the database
        typeArticleRepository.saveAndFlush(typeArticle);

        int databaseSizeBeforeDelete = typeArticleRepository.findAll().size();

        // Delete the typeArticle
        restTypeArticleMockMvc.perform(delete("/api/type-articles/{id}", typeArticle.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeArticle> typeArticleList = typeArticleRepository.findAll();
        assertThat(typeArticleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
