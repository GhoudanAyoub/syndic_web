package com.syndicg5.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Depense.
 */
@Document(collection = "depense")
public class Depense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("montant")
    private Double montant;

    @Field("date")
    private LocalDate date;

    @Field("description")
    private String description;

    @DBRef
    @Field("categorie")
    @JsonIgnoreProperties(value = { "depense", "revenu" }, allowSetters = true)
    private Set<Categorie> categories = new HashSet<>();

    @DBRef
    @Field("immeuble")
    @JsonIgnoreProperties(value = { "appartements", "syndic", "depenses", "revenus" }, allowSetters = true)
    private Immeuble immeuble;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Depense id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getMontant() {
        return this.montant;
    }

    public Depense montant(Double montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Depense date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public Depense description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Categorie> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Categorie> categories) {
        if (this.categories != null) {
            this.categories.forEach(i -> i.setDepense(null));
        }
        if (categories != null) {
            categories.forEach(i -> i.setDepense(this));
        }
        this.categories = categories;
    }

    public Depense categories(Set<Categorie> categories) {
        this.setCategories(categories);
        return this;
    }

    public Depense addCategorie(Categorie categorie) {
        this.categories.add(categorie);
        categorie.setDepense(this);
        return this;
    }

    public Depense removeCategorie(Categorie categorie) {
        this.categories.remove(categorie);
        categorie.setDepense(null);
        return this;
    }

    public Immeuble getImmeuble() {
        return this.immeuble;
    }

    public void setImmeuble(Immeuble immeuble) {
        this.immeuble = immeuble;
    }

    public Depense immeuble(Immeuble immeuble) {
        this.setImmeuble(immeuble);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Depense)) {
            return false;
        }
        return id != null && id.equals(((Depense) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Depense{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
