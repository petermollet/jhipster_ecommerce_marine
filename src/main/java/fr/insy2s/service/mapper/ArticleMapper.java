package fr.insy2s.service.mapper;


import fr.insy2s.domain.*;
import fr.insy2s.service.dto.ArticleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Article} and its DTO {@link ArticleDTO}.
 */
@Mapper(componentModel = "spring", uses = {TypeArticleMapper.class})
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {

    @Mapping(source = "typeArticle.id", target = "typeArticleId")
    ArticleDTO toDto(Article article);

    @Mapping(source = "typeArticleId", target = "typeArticle")
    @Mapping(target = "paniers", ignore = true)
    @Mapping(target = "removePanier", ignore = true)
    @Mapping(target = "panierHistoriques", ignore = true)
    @Mapping(target = "removePanierHistorique", ignore = true)
    Article toEntity(ArticleDTO articleDTO);

    default Article fromId(Long id) {
        if (id == null) {
            return null;
        }
        Article article = new Article();
        article.setId(id);
        return article;
    }
}
