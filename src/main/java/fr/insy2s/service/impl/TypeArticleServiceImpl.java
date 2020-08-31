package fr.insy2s.service.impl;

import fr.insy2s.service.TypeArticleService;
import fr.insy2s.domain.TypeArticle;
import fr.insy2s.repository.TypeArticleRepository;
import fr.insy2s.service.dto.TypeArticleDTO;
import fr.insy2s.service.mapper.TypeArticleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TypeArticle}.
 */
@Service
@Transactional
public class TypeArticleServiceImpl implements TypeArticleService {

    private final Logger log = LoggerFactory.getLogger(TypeArticleServiceImpl.class);

    private final TypeArticleRepository typeArticleRepository;

    private final TypeArticleMapper typeArticleMapper;

    public TypeArticleServiceImpl(TypeArticleRepository typeArticleRepository, TypeArticleMapper typeArticleMapper) {
        this.typeArticleRepository = typeArticleRepository;
        this.typeArticleMapper = typeArticleMapper;
    }

    /**
     * Save a typeArticle.
     *
     * @param typeArticleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeArticleDTO save(TypeArticleDTO typeArticleDTO) {
        log.debug("Request to save TypeArticle : {}", typeArticleDTO);
        TypeArticle typeArticle = typeArticleMapper.toEntity(typeArticleDTO);
        typeArticle = typeArticleRepository.save(typeArticle);
        return typeArticleMapper.toDto(typeArticle);
    }

    /**
     * Get all the typeArticles.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TypeArticleDTO> findAll() {
        log.debug("Request to get all TypeArticles");
        return typeArticleRepository.findAll().stream()
            .map(typeArticleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one typeArticle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeArticleDTO> findOne(Long id) {
        log.debug("Request to get TypeArticle : {}", id);
        return typeArticleRepository.findById(id)
            .map(typeArticleMapper::toDto);
    }

    /**
     * Delete the typeArticle by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeArticle : {}", id);
        typeArticleRepository.deleteById(id);
    }
}
