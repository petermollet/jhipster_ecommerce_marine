package fr.insy2s.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    @Column(name = "libelle", length = 30, nullable = false)
    private String libelle;

    @Column(name = "prix")
    private Integer prix;

    @ManyToOne
    @JsonIgnoreProperties("articles")
    private TypeArticle typeArticle;

    @ManyToMany(mappedBy = "articles")
    @JsonIgnore
    private Set<Panier> paniers = new HashSet<>();

    @ManyToMany(mappedBy = "articleHistoriques")
    @JsonIgnore
    private Set<Historique> panierHistoriques = new HashSet<>();

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

    public Article libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getPrix() {
        return prix;
    }

    public Article prix(Integer prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public TypeArticle getTypeArticle() {
        return typeArticle;
    }

    public Article typeArticle(TypeArticle typeArticle) {
        this.typeArticle = typeArticle;
        return this;
    }

    public void setTypeArticle(TypeArticle typeArticle) {
        this.typeArticle = typeArticle;
    }

    public Set<Panier> getPaniers() {
        return paniers;
    }

    public Article paniers(Set<Panier> paniers) {
        this.paniers = paniers;
        return this;
    }

    public Article addPanier(Panier panier) {
        this.paniers.add(panier);
        panier.getArticles().add(this);
        return this;
    }

    public Article removePanier(Panier panier) {
        this.paniers.remove(panier);
        panier.getArticles().remove(this);
        return this;
    }

    public void setPaniers(Set<Panier> paniers) {
        this.paniers = paniers;
    }

    public Set<Historique> getPanierHistoriques() {
        return panierHistoriques;
    }

    public Article panierHistoriques(Set<Historique> historiques) {
        this.panierHistoriques = historiques;
        return this;
    }

    public Article addPanierHistorique(Historique historique) {
        this.panierHistoriques.add(historique);
        historique.getArticleHistoriques().add(this);
        return this;
    }

    public Article removePanierHistorique(Historique historique) {
        this.panierHistoriques.remove(historique);
        historique.getArticleHistoriques().remove(this);
        return this;
    }

    public void setPanierHistoriques(Set<Historique> historiques) {
        this.panierHistoriques = historiques;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return id != null && id.equals(((Article) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", prix=" + getPrix() +
            "}";
    }
}
