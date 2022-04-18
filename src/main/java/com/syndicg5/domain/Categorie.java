package com.syndicg5.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Categorie.
 */
@Document(collection = "categorie")
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("libelle")
    private String libelle;

    @DBRef
    @Field("depense")
    @JsonIgnoreProperties(value = { "categories", "immeuble" }, allowSetters = true)
    private Depense depense;

    @DBRef
    @Field("revenu")
    @JsonIgnoreProperties(value = { "categories", "immeuble" }, allowSetters = true)
    private Revenu revenu;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Categorie id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Categorie libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Depense getDepense() {
        return this.depense;
    }

    public void setDepense(Depense depense) {
        this.depense = depense;
    }

    public Categorie depense(Depense depense) {
        this.setDepense(depense);
        return this;
    }

    public Revenu getRevenu() {
        return this.revenu;
    }

    public void setRevenu(Revenu revenu) {
        this.revenu = revenu;
    }

    public Categorie revenu(Revenu revenu) {
        this.setRevenu(revenu);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categorie)) {
            return false;
        }
        return id != null && id.equals(((Categorie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categorie{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
