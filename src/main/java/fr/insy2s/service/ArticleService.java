package fr.insy2s.service;

import fr.insy2s.service.dto.ArticleDTO;
import fr.insy2s.utils.wrappers.ArticleWrapper;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.insy2s.domain.Article}.
 */
public interface ArticleService {

    /**
     * Save a article.
     *
     * @param articleDTO the entity to save.
     * @return the persisted entity.
     */
    ArticleDTO save(ArticleDTO articleDTO);

    /**
     * Get all the articles.
     *
     * @return the list of entities.
     */
    List<ArticleDTO> findAll();

    /**
     * Get the "id" article.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticleDTO> findOne(Long id);

    /**
     * Delete the "id" article.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    

    /**
     * recupere tous les articles d'un type d'article
     * @param idTypeArticle : l'id du type de l'article
     * @return une liste d'entitées
     */
	List<ArticleDTO> findAllByTypeArticleId(Long idTypeArticle);

	/**
     * recupere tous les articles d'un type d'article acheté par un client
     * @param idTypeArticle : l'id du type de l'article
     *  * @param login : le login du client
     * @return une liste d'entitées
     */
	List<ArticleDTO> findAllByTypeArticleIdAndClient(Long idTypeArticle, String login);

	/**
	 * recupere un wrapper de l'article correspondant à l'id
	 * @param id l'id de l'article
	 * @return le wrapper 
	 */
	Optional<ArticleWrapper> findOneWrapper(Long id);
}
