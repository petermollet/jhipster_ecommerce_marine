package fr.insy2s.service.impl;

import fr.insy2s.service.ArticleService;
import fr.insy2s.domain.Article;
import fr.insy2s.repository.ArticleRepository;
import fr.insy2s.repository.TypeArticleRepository;
import fr.insy2s.service.dto.ArticleDTO;
import fr.insy2s.service.dto.TypeArticleDTO;
import fr.insy2s.service.mapper.ArticleMapper;
import fr.insy2s.service.mapper.TypeArticleMapper;
import fr.insy2s.utils.wrappers.ArticleWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Article}.
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;
    
    private final TypeArticleRepository typeArticleRepository;

    private final ArticleMapper articleMapper;
    
    private final TypeArticleMapper typeArticleMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper, TypeArticleRepository typeArticleRepository,TypeArticleMapper typeArticleMapper) {
		this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.typeArticleRepository = typeArticleRepository;
        this.typeArticleMapper = typeArticleMapper;
    }

    /**
     * Save a article.
     *
     * @param articleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ArticleDTO save(ArticleDTO articleDTO) {
        log.debug("Request to save Article : {}", articleDTO);
        Article article = articleMapper.toEntity(articleDTO);
        article = articleRepository.save(article);
        return articleMapper.toDto(article);
    }

    /**
     * Get all the articles.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ArticleDTO> findAll() {
        log.debug("Request to get all Articles");
        return articleRepository.findAll().stream()
            .map(articleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one article by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ArticleDTO> findOne(Long id) {
        log.debug("Request to get Article : {}", id);
        return articleRepository.findById(id)
            .map(articleMapper::toDto);
    }

    /**
     * Delete the article by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Article : {}", id);
        articleRepository.deleteById(id);
    }
    
    

    /**
     *tous les articles d'un type d'article (id)
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ArticleDTO> findAllByTypeArticleId(Long idTypeArticle) {
        log.debug("Request pour avoir tous les articles d'un type d'article");
        return articleRepository.findAllByTypeArticleId(idTypeArticle).stream()
            .map(articleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     *tous les articles d'un type d'article (id) et acheté par un user (login)
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ArticleDTO> findAllByTypeArticleIdAndClient(Long idTypeArticle,String login) {
        log.debug("Request pour avoir tous les articles d'un type d'article d'un panier d'un client");
      
        return articleRepository.findAllByTypeArticleIdAndPaniersClientLogin(idTypeArticle,login).stream()
            .map(articleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     * Get one article wrapper by id.
     *
     * @param id the id of the entity.
     * @return the entity wrapper.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ArticleWrapper> findOneWrapper(Long id) {
        log.debug("Request to get Article wrapper : {}", id);
        Optional<ArticleDTO> articleDto =  articleRepository.findById(id).map(articleMapper::toDto);
        Optional<TypeArticleDTO> typeArticleDto = Optional.empty();
        ArticleWrapper articleWrapper=null;
     
        if(articleDto.isPresent() && articleDto.get().getTypeArticleId()!=null) {
        	typeArticleDto=typeArticleRepository.findById(articleDto.get().getTypeArticleId()).map(typeArticleMapper::toDto);
        	articleWrapper = new ArticleWrapper(articleDto.orElse(null), typeArticleDto.orElse(null));  
        }
        return Optional.ofNullable(articleWrapper);
    }
}
