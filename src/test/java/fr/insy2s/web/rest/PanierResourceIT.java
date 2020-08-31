package fr.insy2s.web.rest;

import fr.insy2s.ECommerceJhipsterV1App;
import fr.insy2s.domain.Panier;
import fr.insy2s.repository.PanierRepository;
import fr.insy2s.service.PanierService;
import fr.insy2s.service.dto.PanierDTO;
import fr.insy2s.service.mapper.PanierMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PanierResource} REST controller.
 */
@SpringBootTest(classes = ECommerceJhipsterV1App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PanierResourceIT {

    private static final Instant DEFAULT_DATE_CREATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PanierRepository panierRepository;

    @Mock
    private PanierRepository panierRepositoryMock;

    @Autowired
    private PanierMapper panierMapper;

    @Mock
    private PanierService panierServiceMock;

    @Autowired
    private PanierService panierService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPanierMockMvc;

    private Panier panier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Panier createEntity(EntityManager em) {
        Panier panier = new Panier()
            .dateCreation(DEFAULT_DATE_CREATION);
        return panier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Panier createUpdatedEntity(EntityManager em) {
        Panier panier = new Panier()
            .dateCreation(UPDATED_DATE_CREATION);
        return panier;
    }

    @BeforeEach
    public void initTest() {
        panier = createEntity(em);
    }

    @Test
    @Transactional
    public void createPanier() throws Exception {
        int databaseSizeBeforeCreate = panierRepository.findAll().size();

        // Create the Panier
        PanierDTO panierDTO = panierMapper.toDto(panier);
        restPanierMockMvc.perform(post("/api/paniers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(panierDTO)))
            .andExpect(status().isCreated());

        // Validate the Panier in the database
        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeCreate + 1);
        Panier testPanier = panierList.get(panierList.size() - 1);
        assertThat(testPanier.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
    }

    @Test
    @Transactional
    public void createPanierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = panierRepository.findAll().size();

        // Create the Panier with an existing ID
        panier.setId(1L);
        PanierDTO panierDTO = panierMapper.toDto(panier);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPanierMockMvc.perform(post("/api/paniers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(panierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Panier in the database
        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPaniers() throws Exception {
        // Initialize the database
        panierRepository.saveAndFlush(panier);

        // Get all the panierList
        restPanierMockMvc.perform(get("/api/paniers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(panier.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPaniersWithEagerRelationshipsIsEnabled() throws Exception {
        when(panierServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPanierMockMvc.perform(get("/api/paniers?eagerload=true"))
            .andExpect(status().isOk());

        verify(panierServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPaniersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(panierServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPanierMockMvc.perform(get("/api/paniers?eagerload=true"))
            .andExpect(status().isOk());

        verify(panierServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPanier() throws Exception {
        // Initialize the database
        panierRepository.saveAndFlush(panier);

        // Get the panier
        restPanierMockMvc.perform(get("/api/paniers/{id}", panier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(panier.getId().intValue()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPanier() throws Exception {
        // Get the panier
        restPanierMockMvc.perform(get("/api/paniers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePanier() throws Exception {
        // Initialize the database
        panierRepository.saveAndFlush(panier);

        int databaseSizeBeforeUpdate = panierRepository.findAll().size();

        // Update the panier
        Panier updatedPanier = panierRepository.findById(panier.getId()).get();
        // Disconnect from session so that the updates on updatedPanier are not directly saved in db
        em.detach(updatedPanier);
        updatedPanier
            .dateCreation(UPDATED_DATE_CREATION);
        PanierDTO panierDTO = panierMapper.toDto(updatedPanier);

        restPanierMockMvc.perform(put("/api/paniers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(panierDTO)))
            .andExpect(status().isOk());

        // Validate the Panier in the database
        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeUpdate);
        Panier testPanier = panierList.get(panierList.size() - 1);
        assertThat(testPanier.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void updateNonExistingPanier() throws Exception {
        int databaseSizeBeforeUpdate = panierRepository.findAll().size();

        // Create the Panier
        PanierDTO panierDTO = panierMapper.toDto(panier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPanierMockMvc.perform(put("/api/paniers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(panierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Panier in the database
        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePanier() throws Exception {
        // Initialize the database
        panierRepository.saveAndFlush(panier);

        int databaseSizeBeforeDelete = panierRepository.findAll().size();

        // Delete the panier
        restPanierMockMvc.perform(delete("/api/paniers/{id}", panier.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
