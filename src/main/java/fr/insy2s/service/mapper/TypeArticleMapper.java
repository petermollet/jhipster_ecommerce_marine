package fr.insy2s.service.mapper;


import fr.insy2s.domain.*;
import fr.insy2s.service.dto.TypeArticleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeArticle} and its DTO {@link TypeArticleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeArticleMapper extends EntityMapper<TypeArticleDTO, TypeArticle> {


    @Mapping(target = "articles", ignore = true)
    @Mapping(target = "removeArticle", ignore = true)
    TypeArticle toEntity(TypeArticleDTO typeArticleDTO);

    default TypeArticle fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeArticle typeArticle = new TypeArticle();
        typeArticle.setId(id);
        return typeArticle;
    }
}
