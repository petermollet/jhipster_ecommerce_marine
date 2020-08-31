package fr.insy2s.repository;

import fr.insy2s.domain.TypeArticle;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeArticle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeArticleRepository extends JpaRepository<TypeArticle, Long> {
}
