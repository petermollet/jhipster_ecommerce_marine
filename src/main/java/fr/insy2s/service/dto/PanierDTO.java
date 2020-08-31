package fr.insy2s.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link fr.insy2s.domain.Panier} entity.
 */
public class PanierDTO implements Serializable {
    
    private Long id;

    private Instant dateCreation;


    private Long clientId;
    private Set<ArticleDTO> articles = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long userId) {
        this.clientId = userId;
    }

    public Set<ArticleDTO> getArticles() {
        return articles;
    }

    public void setArticles(Set<ArticleDTO> articles) {
        this.articles = articles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PanierDTO panierDTO = (PanierDTO) o;
        if (panierDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), panierDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PanierDTO{" +
            "id=" + getId() +
            ", dateCreation='" + getDateCreation() + "'" +
            ", clientId=" + getClientId() +
            ", articles='" + getArticles() + "'" +
            "}";
    }
}
