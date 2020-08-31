package fr.insy2s.web.rest;

import fr.insy2s.service.HistoriqueService;
import fr.insy2s.web.rest.errors.BadRequestAlertException;
import fr.insy2s.service.dto.HistoriqueDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.insy2s.domain.Historique}.
 */
@RestController
@RequestMapping("/api")
public class HistoriqueResource {

    private final Logger log = LoggerFactory.getLogger(HistoriqueResource.class);

    private static final String ENTITY_NAME = "historique";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoriqueService historiqueService;

    public HistoriqueResource(HistoriqueService historiqueService) {
        this.historiqueService = historiqueService;
    }

    /**
     * {@code POST  /historiques} : Create a new historique.
     *
     * @param historiqueDTO the historiqueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historiqueDTO, or with status {@code 400 (Bad Request)} if the historique has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/historiques")
    public ResponseEntity<HistoriqueDTO> createHistorique(@RequestBody HistoriqueDTO historiqueDTO) throws URISyntaxException {
        log.debug("REST request to save Historique : {}", historiqueDTO);
        if (historiqueDTO.getId() != null) {
            throw new BadRequestAlertException("A new historique cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoriqueDTO result = historiqueService.save(historiqueDTO);
        return ResponseEntity.created(new URI("/api/historiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /historiques} : Updates an existing historique.
     *
     * @param historiqueDTO the historiqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historiqueDTO,
     * or with status {@code 400 (Bad Request)} if the historiqueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historiqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/historiques")
    public ResponseEntity<HistoriqueDTO> updateHistorique(@RequestBody HistoriqueDTO historiqueDTO) throws URISyntaxException {
        log.debug("REST request to update Historique : {}", historiqueDTO);
        if (historiqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HistoriqueDTO result = historiqueService.save(historiqueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historiqueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /historiques} : get all the historiques.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historiques in body.
     */
    @GetMapping("/historiques")
    public ResponseEntity<List<HistoriqueDTO>> getAllHistoriques(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Historiques");
        Page<HistoriqueDTO> page;
        if (eagerload) {
            page = historiqueService.findAllWithEagerRelationships(pageable);
        } else {
            page = historiqueService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /historiques/:id} : get the "id" historique.
     *
     * @param id the id of the historiqueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historiqueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/historiques/{id}")
    public ResponseEntity<HistoriqueDTO> getHistorique(@PathVariable Long id) {
        log.debug("REST request to get Historique : {}", id);
        Optional<HistoriqueDTO> historiqueDTO = historiqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(historiqueDTO);
    }

    /**
     * {@code DELETE  /historiques/:id} : delete the "id" historique.
     *
     * @param id the id of the historiqueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/historiques/{id}")
    public ResponseEntity<Void> deleteHistorique(@PathVariable Long id) {
        log.debug("REST request to delete Historique : {}", id);
        historiqueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
