package fr.insy2s.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeArticle.
 */
@Entity
@Table(name = "type_article")
public class TypeArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    @Column(name = "libelle", length = 30, nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "typeArticle")
    private Set<Article> articles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public TypeArticle libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public TypeArticle articles(Set<Article> articles) {
        this.articles = articles;
        return this;
    }

    public TypeArticle addArticle(Article article) {
        this.articles.add(article);
        article.setTypeArticle(this);
        return this;
    }

    public TypeArticle removeArticle(Article article) {
        this.articles.remove(article);
        article.setTypeArticle(null);
        return this;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeArticle)) {
            return false;
        }
        return id != null && id.equals(((TypeArticle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeArticle{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
