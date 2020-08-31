package fr.insy2s.service.mapper;


import fr.insy2s.domain.*;
import fr.insy2s.service.dto.PanierDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Panier} and its DTO {@link PanierDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ArticleMapper.class})
public interface PanierMapper extends EntityMapper<PanierDTO, Panier> {

    @Mapping(source = "client.id", target = "clientId")
    PanierDTO toDto(Panier panier);

    @Mapping(source = "clientId", target = "client")
    @Mapping(target = "removeArticle", ignore = true)
    Panier toEntity(PanierDTO panierDTO);

    default Panier fromId(Long id) {
        if (id == null) {
            return null;
        }
        Panier panier = new Panier();
        panier.setId(id);
        return panier;
    }
}
