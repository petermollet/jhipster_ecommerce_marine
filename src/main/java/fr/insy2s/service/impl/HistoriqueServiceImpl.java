package fr.insy2s.service.impl;

import fr.insy2s.service.HistoriqueService;
import fr.insy2s.domain.Historique;
import fr.insy2s.repository.HistoriqueRepository;
import fr.insy2s.service.dto.HistoriqueDTO;
import fr.insy2s.service.mapper.HistoriqueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Historique}.
 */
@Service
@Transactional
public class HistoriqueServiceImpl implements HistoriqueService {

    private final Logger log = LoggerFactory.getLogger(HistoriqueServiceImpl.class);

    private final HistoriqueRepository historiqueRepository;

    private final HistoriqueMapper historiqueMapper;

    public HistoriqueServiceImpl(HistoriqueRepository historiqueRepository, HistoriqueMapper historiqueMapper) {
        this.historiqueRepository = historiqueRepository;
        this.historiqueMapper = historiqueMapper;
    }

    /**
     * Save a historique.
     *
     * @param historiqueDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HistoriqueDTO save(HistoriqueDTO historiqueDTO) {
        log.debug("Request to save Historique : {}", historiqueDTO);
        Historique historique = historiqueMapper.toEntity(historiqueDTO);
        historique = historiqueRepository.save(historique);
        return historiqueMapper.toDto(historique);
    }

    /**
     * Get all the historiques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HistoriqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Historiques");
        return historiqueRepository.findAll(pageable)
            .map(historiqueMapper::toDto);
    }

    /**
     * Get all the historiques with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<HistoriqueDTO> findAllWithEagerRelationships(Pageable pageable) {
        return historiqueRepository.findAllWithEagerRelationships(pageable).map(historiqueMapper::toDto);
    }

    /**
     * Get one historique by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HistoriqueDTO> findOne(Long id) {
        log.debug("Request to get Historique : {}", id);
        return historiqueRepository.findOneWithEagerRelationships(id)
            .map(historiqueMapper::toDto);
    }

    /**
     * Delete the historique by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Historique : {}", id);
        historiqueRepository.deleteById(id);
    }
}
