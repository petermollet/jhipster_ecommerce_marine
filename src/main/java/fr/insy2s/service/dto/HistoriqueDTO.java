package fr.insy2s.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link fr.insy2s.domain.Historique} entity.
 */
public class HistoriqueDTO implements Serializable {
    
    private Long id;

    private Instant dateValidation;


    private Long clientId;
    private Set<ArticleDTO> articleHistoriques = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(Instant dateValidation) {
        this.dateValidation = dateValidation;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long userId) {
        this.clientId = userId;
    }

    public Set<ArticleDTO> getArticleHistoriques() {
        return articleHistoriques;
    }

    public void setArticleHistoriques(Set<ArticleDTO> articles) {
        this.articleHistoriques = articles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HistoriqueDTO historiqueDTO = (HistoriqueDTO) o;
        if (historiqueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), historiqueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HistoriqueDTO{" +
            "id=" + getId() +
            ", dateValidation='" + getDateValidation() + "'" +
            ", clientId=" + getClientId() +
            ", articleHistoriques='" + getArticleHistoriques() + "'" +
            "}";
    }
}
