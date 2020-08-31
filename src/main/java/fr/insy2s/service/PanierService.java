package fr.insy2s.service;

import fr.insy2s.service.dto.PanierDTO;
import fr.insy2s.utils.wrappers.PanierWrapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.insy2s.domain.Panier}.
 */
public interface PanierService {

    /**
     * Save a panier.
     *
     * @param panierDTO the entity to save.
     * @return the persisted entity.
     */
    PanierDTO save(PanierDTO panierDTO);

    /**
     * Get all the paniers.
     *
     * @return the list of entities.
     */
    List<PanierDTO> findAll();

    /**
     * Get all the paniers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<PanierDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" panier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PanierDTO> findOne(Long id);

    /**
     * Delete the "id" panier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * recupere le panier correspondant au login du client
     *
     * @param login le login du client
     * @return le panier.
     */
	Optional<PanierDTO> findByClientLogin(String login);

	/**
     * recupere le panier correspondant à la personne authentifiée
     *
     * @param login le login de la personne authentifiée
     * @return le panier.
     */
	Optional<PanierWrapper> getPanierClientAuth(String login);
}
