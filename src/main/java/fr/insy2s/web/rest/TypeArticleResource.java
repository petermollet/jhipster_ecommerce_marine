package fr.insy2s.web.rest;

import fr.insy2s.service.TypeArticleService;
import fr.insy2s.web.rest.errors.BadRequestAlertException;
import fr.insy2s.service.dto.TypeArticleDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.insy2s.domain.TypeArticle}.
 */
@RestController
@RequestMapping("/api")
public class TypeArticleResource {

    private final Logger log = LoggerFactory.getLogger(TypeArticleResource.class);

    private static final String ENTITY_NAME = "typeArticle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeArticleService typeArticleService;

    public TypeArticleResource(TypeArticleService typeArticleService) {
        this.typeArticleService = typeArticleService;
    }

    /**
     * {@code POST  /type-articles} : Create a new typeArticle.
     *
     * @param typeArticleDTO the typeArticleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeArticleDTO, or with status {@code 400 (Bad Request)} if the typeArticle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-articles")
    public ResponseEntity<TypeArticleDTO> createTypeArticle(@Valid @RequestBody TypeArticleDTO typeArticleDTO) throws URISyntaxException {
        log.debug("REST request to save TypeArticle : {}", typeArticleDTO);
        if (typeArticleDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeArticle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeArticleDTO result = typeArticleService.save(typeArticleDTO);
        return ResponseEntity.created(new URI("/api/type-articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-articles} : Updates an existing typeArticle.
     *
     * @param typeArticleDTO the typeArticleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeArticleDTO,
     * or with status {@code 400 (Bad Request)} if the typeArticleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeArticleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-articles")
    public ResponseEntity<TypeArticleDTO> updateTypeArticle(@Valid @RequestBody TypeArticleDTO typeArticleDTO) throws URISyntaxException {
        log.debug("REST request to update TypeArticle : {}", typeArticleDTO);
        if (typeArticleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeArticleDTO result = typeArticleService.save(typeArticleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeArticleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-articles} : get all the typeArticles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeArticles in body.
     */
    @GetMapping("/type-articles")
    public List<TypeArticleDTO> getAllTypeArticles() {
        log.debug("REST request to get all TypeArticles");
        return typeArticleService.findAll();
    }

    /**
     * {@code GET  /type-articles/:id} : get the "id" typeArticle.
     *
     * @param id the id of the typeArticleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeArticleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-articles/{id}")
    public ResponseEntity<TypeArticleDTO> getTypeArticle(@PathVariable Long id) {
        log.debug("REST request to get TypeArticle : {}", id);
        Optional<TypeArticleDTO> typeArticleDTO = typeArticleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeArticleDTO);
    }

    /**
     * {@code DELETE  /type-articles/:id} : delete the "id" typeArticle.
     *
     * @param id the id of the typeArticleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-articles/{id}")
    public ResponseEntity<Void> deleteTypeArticle(@PathVariable Long id) {
        log.debug("REST request to delete TypeArticle : {}", id);
        typeArticleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
