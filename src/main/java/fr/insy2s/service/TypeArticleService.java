package fr.insy2s.service;

import fr.insy2s.service.dto.TypeArticleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.insy2s.domain.TypeArticle}.
 */
public interface TypeArticleService {

    /**
     * Save a typeArticle.
     *
     * @param typeArticleDTO the entity to save.
     * @return the persisted entity.
     */
    TypeArticleDTO save(TypeArticleDTO typeArticleDTO);

    /**
     * Get all the typeArticles.
     *
     * @return the list of entities.
     */
    List<TypeArticleDTO> findAll();

    /**
     * Get the "id" typeArticle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeArticleDTO> findOne(Long id);

    /**
     * Delete the "id" typeArticle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
