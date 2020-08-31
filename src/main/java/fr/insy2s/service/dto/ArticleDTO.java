package fr.insy2s.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.insy2s.domain.Article} entity.
 */
public class ArticleDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    private String libelle;

    private Integer prix;


    private Long typeArticleId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Long getTypeArticleId() {
        return typeArticleId;
    }

    public void setTypeArticleId(Long typeArticleId) {
        this.typeArticleId = typeArticleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArticleDTO articleDTO = (ArticleDTO) o;
        if (articleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), articleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArticleDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", prix=" + getPrix() +
            ", typeArticleId=" + getTypeArticleId() +
            "}";
    }
}
