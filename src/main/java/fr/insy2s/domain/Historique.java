package fr.insy2s.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Historique.
 */
@Entity
@Table(name = "historique")
public class Historique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_validation")
    private Instant dateValidation;

    @ManyToOne
    @JsonIgnoreProperties("historiques")
    private User client;

    @ManyToMany
    @JoinTable(name = "historique_article_historique",
               joinColumns = @JoinColumn(name = "historique_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "article_historique_id", referencedColumnName = "id"))
    private Set<Article> articleHistoriques = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateValidation() {
        return dateValidation;
    }

    public Historique dateValidation(Instant dateValidation) {
        this.dateValidation = dateValidation;
        return this;
    }

    public void setDateValidation(Instant dateValidation) {
        this.dateValidation = dateValidation;
    }

    public User getClient() {
        return client;
    }

    public Historique client(User user) {
        this.client = user;
        return this;
    }

    public void setClient(User user) {
        this.client = user;
    }

    public Set<Article> getArticleHistoriques() {
        return articleHistoriques;
    }

    public Historique articleHistoriques(Set<Article> articles) {
        this.articleHistoriques = articles;
        return this;
    }

    public Historique addArticleHistorique(Article article) {
        this.articleHistoriques.add(article);
        article.getPanierHistoriques().add(this);
        return this;
    }

    public Historique removeArticleHistorique(Article article) {
        this.articleHistoriques.remove(article);
        article.getPanierHistoriques().remove(this);
        return this;
    }

    public void setArticleHistoriques(Set<Article> articles) {
        this.articleHistoriques = articles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Historique)) {
            return false;
        }
        return id != null && id.equals(((Historique) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Historique{" +
            "id=" + getId() +
            ", dateValidation='" + getDateValidation() + "'" +
            "}";
    }
}
