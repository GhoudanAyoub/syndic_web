package com.syndicg5.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Syndic.
 */
@Document(collection = "syndic")
public class Syndic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @DBRef
    @Field("personne")
    private Personne personne;

    @DBRef
    @Field("immeuble")
    @JsonIgnoreProperties(value = { "appartements", "syndic", "depenses", "revenus" }, allowSetters = true)
    private Set<Immeuble> immeubles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Syndic id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Personne getPersonne() {
        return this.personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Syndic personne(Personne personne) {
        this.setPersonne(personne);
        return this;
    }

    public Set<Immeuble> getImmeubles() {
        return this.immeubles;
    }

    public void setImmeubles(Set<Immeuble> immeubles) {
        if (this.immeubles != null) {
            this.immeubles.forEach(i -> i.setSyndic(null));
        }
        if (immeubles != null) {
            immeubles.forEach(i -> i.setSyndic(this));
        }
        this.immeubles = immeubles;
    }

    public Syndic immeubles(Set<Immeuble> immeubles) {
        this.setImmeubles(immeubles);
        return this;
    }

    public Syndic addImmeuble(Immeuble immeuble) {
        this.immeubles.add(immeuble);
        immeuble.setSyndic(this);
        return this;
    }

    public Syndic removeImmeuble(Immeuble immeuble) {
        this.immeubles.remove(immeuble);
        immeuble.setSyndic(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Syndic)) {
            return false;
        }
        return id != null && id.equals(((Syndic) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Syndic{" +
            "id=" + getId() +
            "}";
    }
}
