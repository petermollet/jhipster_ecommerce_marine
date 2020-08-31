package fr.insy2s.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Panier.
 */
@Entity
@Table(name = "panier")
public class Panier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_creation")
    private Instant dateCreation;

    @OneToOne
    @JoinColumn(unique = true)
    private User client;

    @ManyToMany
    @JoinTable(name = "panier_article",
               joinColumns = @JoinColumn(name = "panier_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"))
    private Set<Article> articles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateCreation() {
        return dateCreation;
    }

    public Panier dateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
    }

    public User getClient() {
        return client;
    }

    public Panier client(User user) {
        this.client = user;
        return this;
    }

    public void setClient(User user) {
        this.client = user;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public Panier articles(Set<Article> articles) {
        this.articles = articles;
        return this;
    }

    public Panier addArticle(Article article) {
        this.articles.add(article);
        article.getPaniers().add(this);
        return this;
    }

    public Panier removeArticle(Article article) {
        this.articles.remove(article);
        article.getPaniers().remove(this);
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
        if (!(o instanceof Panier)) {
            return false;
        }
        return id != null && id.equals(((Panier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Panier{" +
            "id=" + getId() +
            ", dateCreation='" + getDateCreation() + "'" +
            "}";
    }
}
