package fr.insy2s.repository;

import fr.insy2s.domain.Historique;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Historique entity.
 */
@Repository
public interface HistoriqueRepository extends JpaRepository<Historique, Long> {

    @Query("select historique from Historique historique where historique.client.login = ?#{principal.username}")
    List<Historique> findByClientIsCurrentUser();

    @Query(value = "select distinct historique from Historique historique left join fetch historique.articleHistoriques",
        countQuery = "select count(distinct historique) from Historique historique")
    Page<Historique> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct historique from Historique historique left join fetch historique.articleHistoriques")
    List<Historique> findAllWithEagerRelationships();

    @Query("select historique from Historique historique left join fetch historique.articleHistoriques where historique.id =:id")
    Optional<Historique> findOneWithEagerRelationships(@Param("id") Long id);
}
