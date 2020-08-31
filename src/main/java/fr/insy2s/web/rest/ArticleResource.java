package fr.insy2s.web.rest;

import fr.insy2s.service.ArticleService;
import fr.insy2s.web.rest.errors.BadRequestAlertException;
import fr.insy2s.service.dto.ArticleDTO;
import fr.insy2s.utils.wrappers.ArticleWrapper;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.insy2s.domain.Article}.
 */
@RestController
@RequestMapping("/api")
public class ArticleResource {

    private final Logger log = LoggerFactory.getLogger(ArticleResource.class);

    private static final String ENTITY_NAME = "article";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticleService articleService;

    public ArticleResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * {@code POST  /articles} : Create a new article.
     *
     * @param articleDTO the articleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articleDTO, or with status {@code 400 (Bad Request)} if the article has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/articles")
    public ResponseEntity<ArticleDTO> createArticle(@Valid @RequestBody ArticleDTO articleDTO) throws URISyntaxException {
        log.debug("REST request to save Article : {}", articleDTO);
        if (articleDTO.getId() != null) {
            throw new BadRequestAlertException("A new article cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticleDTO result = articleService.save(articleDTO);
        return ResponseEntity.created(new URI("/api/articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /articles} : Updates an existing article.
     *
     * @param articleDTO the articleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleDTO,
     * or with status {@code 400 (Bad Request)} if the articleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/articles")
    public ResponseEntity<ArticleDTO> updateArticle(@Valid @RequestBody ArticleDTO articleDTO) throws URISyntaxException {
        log.debug("REST request to update Article : {}", articleDTO);
        if (articleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArticleDTO result = articleService.save(articleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /articles} : get all the articles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articles in body.
     */
    @GetMapping("/articles")
    public List<ArticleDTO> getAllArticles() {
        log.debug("REST request to get all Articles");
        return articleService.findAll();
    }

    /**
     * {@code GET  /articles/:id} : get the "id" article.
     *
     * @param id the id of the articleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleDTO> getArticle(@PathVariable Long id) {
        log.debug("REST request to get Article : {}", id);
        Optional<ArticleDTO> articleDTO = articleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articleDTO);
    }

    /**
     * {@code DELETE  /articles/:id} : delete the "id" article.
     *
     * @param id the id of the articleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        log.debug("REST request to delete Article : {}", id);
        articleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    
    
    
    /**
     * {@code GET  /articles/typearticle/:idTypeArticle} : tous les  Articles d'un type d'article.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articles in body.
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/articles/typearticle/{idTypeArticle}")
    public List<ArticleDTO> getAllArticlesTypeArticle(@PathVariable Long idTypeArticle) {
        log.debug("REST request pour avoir tous les  Articles d'un type d'article");
        return articleService.findAllByTypeArticleId(idTypeArticle);
    }
    
    
    /**
     * {@code GET  /articles/typearticle/:idTypeArticle/client/:login} : tous les  Articles d'un type d'article du panier d'un client.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articles in body.
     */
    @GetMapping("/articles/typearticle/{idTypeArticle}/client/{login}")
    public List<ArticleDTO> getAllArticlesTypeArticleIdAndClient(@PathVariable Long idTypeArticle,@PathVariable String login) {
        log.debug("REST request pour avoir tous les  Articles d'un type d'article");
        return articleService.findAllByTypeArticleIdAndClient(idTypeArticle, login);
    }
    
    
    /**
     * {@code GET  /articles/wrapper/:id} : get the "id" article wrapper.
     *
     * @param id the id of the articleWrapper to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/articles/wrapper/{id}")
    public ResponseEntity<ArticleWrapper> getArticleWrapper(@PathVariable Long id) {
        log.debug("REST request to get Article wrapper: {}", id);
        return ResponseUtil.wrapOrNotFound(articleService.findOneWrapper(id));
    }
}
