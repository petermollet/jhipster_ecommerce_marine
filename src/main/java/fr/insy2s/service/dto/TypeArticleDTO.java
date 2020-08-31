package fr.insy2s.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.insy2s.domain.TypeArticle} entity.
 */
public class TypeArticleDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    private String libelle;

    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeArticleDTO typeArticleDTO = (TypeArticleDTO) o;
        if (typeArticleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeArticleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeArticleDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
