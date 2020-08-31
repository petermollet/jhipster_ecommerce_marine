package fr.insy2s.service.mapper;


import fr.insy2s.domain.*;
import fr.insy2s.service.dto.HistoriqueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Historique} and its DTO {@link HistoriqueDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ArticleMapper.class})
public interface HistoriqueMapper extends EntityMapper<HistoriqueDTO, Historique> {

    @Mapping(source = "client.id", target = "clientId")
    HistoriqueDTO toDto(Historique historique);

    @Mapping(source = "clientId", target = "client")
    @Mapping(target = "removeArticleHistorique", ignore = true)
    Historique toEntity(HistoriqueDTO historiqueDTO);

    default Historique fromId(Long id) {
        if (id == null) {
            return null;
        }
        Historique historique = new Historique();
        historique.setId(id);
        return historique;
    }
}
