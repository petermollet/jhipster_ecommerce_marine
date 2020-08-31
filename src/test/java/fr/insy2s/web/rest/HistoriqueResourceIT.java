package fr.insy2s.web.rest;

import fr.insy2s.ECommerceJhipsterV1App;
import fr.insy2s.domain.Historique;
import fr.insy2s.repository.HistoriqueRepository;
import fr.insy2s.service.HistoriqueService;
import fr.insy2s.service.dto.HistoriqueDTO;
import fr.insy2s.service.mapper.HistoriqueMapper;

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
 * Integration tests for the {@link HistoriqueResource} REST controller.
 */
@SpringBootTest(classes = ECommerceJhipsterV1App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class HistoriqueResourceIT {

    private static final Instant DEFAULT_DATE_VALIDATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_VALIDATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private HistoriqueRepository historiqueRepository;

    @Mock
    private HistoriqueRepository historiqueRepositoryMock;

    @Autowired
    private HistoriqueMapper historiqueMapper;

    @Mock
    private HistoriqueService historiqueServiceMock;

    @Autowired
    private HistoriqueService historiqueService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistoriqueMockMvc;

    private Historique historique;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historique createEntity(EntityManager em) {
        Historique historique = new Historique()
            .dateValidation(DEFAULT_DATE_VALIDATION);
        return historique;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historique createUpdatedEntity(EntityManager em) {
        Historique historique = new Historique()
            .dateValidation(UPDATED_DATE_VALIDATION);
        return historique;
    }

    @BeforeEach
    public void initTest() {
        historique = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistorique() throws Exception {
        int databaseSizeBeforeCreate = historiqueRepository.findAll().size();

        // Create the Historique
        HistoriqueDTO historiqueDTO = historiqueMapper.toDto(historique);
        restHistoriqueMockMvc.perform(post("/api/historiques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historiqueDTO)))
            .andExpect(status().isCreated());

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeCreate + 1);
        Historique testHistorique = historiqueList.get(historiqueList.size() - 1);
        assertThat(testHistorique.getDateValidation()).isEqualTo(DEFAULT_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void createHistoriqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historiqueRepository.findAll().size();

        // Create the Historique with an existing ID
        historique.setId(1L);
        HistoriqueDTO historiqueDTO = historiqueMapper.toDto(historique);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoriqueMockMvc.perform(post("/api/historiques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historiqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHistoriques() throws Exception {
        // Initialize the database
        historiqueRepository.saveAndFlush(historique);

        // Get all the historiqueList
        restHistoriqueMockMvc.perform(get("/api/historiques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historique.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(DEFAULT_DATE_VALIDATION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllHistoriquesWithEagerRelationshipsIsEnabled() throws Exception {
        when(historiqueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHistoriqueMockMvc.perform(get("/api/historiques?eagerload=true"))
            .andExpect(status().isOk());

        verify(historiqueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllHistoriquesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(historiqueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHistoriqueMockMvc.perform(get("/api/historiques?eagerload=true"))
            .andExpect(status().isOk());

        verify(historiqueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getHistorique() throws Exception {
        // Initialize the database
        historiqueRepository.saveAndFlush(historique);

        // Get the historique
        restHistoriqueMockMvc.perform(get("/api/historiques/{id}", historique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historique.getId().intValue()))
            .andExpect(jsonPath("$.dateValidation").value(DEFAULT_DATE_VALIDATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHistorique() throws Exception {
        // Get the historique
        restHistoriqueMockMvc.perform(get("/api/historiques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistorique() throws Exception {
        // Initialize the database
        historiqueRepository.saveAndFlush(historique);

        int databaseSizeBeforeUpdate = historiqueRepository.findAll().size();

        // Update the historique
        Historique updatedHistorique = historiqueRepository.findById(historique.getId()).get();
        // Disconnect from session so that the updates on updatedHistorique are not directly saved in db
        em.detach(updatedHistorique);
        updatedHistorique
            .dateValidation(UPDATED_DATE_VALIDATION);
        HistoriqueDTO historiqueDTO = historiqueMapper.toDto(updatedHistorique);

        restHistoriqueMockMvc.perform(put("/api/historiques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historiqueDTO)))
            .andExpect(status().isOk());

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
        Historique testHistorique = historiqueList.get(historiqueList.size() - 1);
        assertThat(testHistorique.getDateValidation()).isEqualTo(UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void updateNonExistingHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().size();

        // Create the Historique
        HistoriqueDTO historiqueDTO = historiqueMapper.toDto(historique);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoriqueMockMvc.perform(put("/api/historiques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historiqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHistorique() throws Exception {
        // Initialize the database
        historiqueRepository.saveAndFlush(historique);

        int databaseSizeBeforeDelete = historiqueRepository.findAll().size();

        // Delete the historique
        restHistoriqueMockMvc.perform(delete("/api/historiques/{id}", historique.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
