package fr.insy2s.service.impl;

import fr.insy2s.service.ArticleService;
import fr.insy2s.service.PanierService;
import fr.insy2s.service.UserService;
import fr.insy2s.domain.Panier;
import fr.insy2s.repository.PanierRepository;
import fr.insy2s.repository.TypeArticleRepository;
import fr.insy2s.repository.UserRepository;
import fr.insy2s.service.dto.PanierDTO;
import fr.insy2s.service.dto.UserDTO;
import fr.insy2s.service.mapper.PanierMapper;
import fr.insy2s.service.mapper.UserMapper;
import fr.insy2s.utils.wrappers.ArticleWrapper;
import fr.insy2s.utils.wrappers.PanierWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Panier}.
 */
@Service
@Transactional
public class PanierServiceImpl implements PanierService {

    private final Logger log = LoggerFactory.getLogger(PanierServiceImpl.class);

    private final PanierRepository panierRepository;

    private final PanierMapper panierMapper;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final ArticleService articleService;

    public PanierServiceImpl(PanierRepository panierRepository, PanierMapper panierMapper,UserMapper userMapper, UserRepository userRepository,ArticleService articleService) {
		this.panierRepository = panierRepository;
        this.panierMapper = panierMapper;
        this.userRepository = userRepository;
		this.articleService = articleService;
		this.userMapper = userMapper;
    }

    /**
     * Save a panier.
     *
     * @param panierDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PanierDTO save(PanierDTO panierDTO) {
        log.debug("Request to save Panier : {}", panierDTO);
        Panier panier = panierMapper.toEntity(panierDTO);
        panier = panierRepository.save(panier);
        return panierMapper.toDto(panier);
    }

    /**
     * Get all the paniers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PanierDTO> findAll() {
        log.debug("Request to get all Paniers");
        return panierRepository.findAllWithEagerRelationships().stream()
            .map(panierMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the paniers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PanierDTO> findAllWithEagerRelationships(Pageable pageable) {
        return panierRepository.findAllWithEagerRelationships(pageable).map(panierMapper::toDto);
    }

    /**
     * Get one panier by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PanierDTO> findOne(Long id) {
        log.debug("Request to get Panier : {}", id);
        return panierRepository.findOneWithEagerRelationships(id)
            .map(panierMapper::toDto);
    }




    /**
     * Delete the panier by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Panier : {}", id);
        panierRepository.deleteById(id);
    }

    /**
     * Get one panier by client login.
     *
     * @param login the login of the client.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PanierDTO> findByClientLogin(String login) {
        log.debug("Request  pour récupérer un panier d'un utilisateur via le login : {}", login);
        return panierRepository.findByClientLogin(login)
            .map(panierMapper::toDto);
    }

    /**
     * Get one panier wrapper by client login.
     *
     * @param login the login of the client.
     * @return the entity wrapper.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PanierWrapper> getPanierClientAuth(String login) {
        log.debug("Request  pour récupérer un panier de l'utilisateur connecté : {}", login);
        PanierWrapper panierWrapper = null;
        Optional<PanierDTO> panierDto = panierRepository.findByClientLogin(login).map(panierMapper::toDto);
        if(panierDto.isPresent()) {
        Optional<UserDTO> userDto = userRepository.findOneByLogin(login).map(userMapper::userToUserDTO);
        List<ArticleWrapper> listeArticle = panierDto.get().getArticles().stream().map(a->articleService.findOneWrapper(a.getId()).orElse(null)).collect(Collectors.toCollection(LinkedList::new));
        panierWrapper = new PanierWrapper(panierDto.get(), userDto.get(), listeArticle);
        }
        return Optional.ofNullable(panierWrapper);
    }
}
