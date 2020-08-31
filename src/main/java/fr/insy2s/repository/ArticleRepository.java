package fr.insy2s.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.insy2s.domain.Article;
import fr.insy2s.service.dto.ArticleDTO;

/**
 * Spring Data  repository for the Article entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

	List<Article> findAllByTypeArticleId(Long idTypeArticle);

	List<Article> findAllByTypeArticleIdAndPaniersClientLogin(Long idTypeArticle, String login);

	
}
