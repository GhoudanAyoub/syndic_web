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
 * A Resident.
 */
@Document(collection = "resident")
public class Resident implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @DBRef
    @Field("personne")
    private Personne personne;

    @DBRef
    @Field("appartement")
    @JsonIgnoreProperties(value = { "payements", "resident", "immeuble" }, allowSetters = true)
    private Set<Appartement> appartements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Resident id(String id) {
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

    public Resident personne(Personne personne) {
        this.setPersonne(personne);
        return this;
    }

    public Set<Appartement> getAppartements() {
        return this.appartements;
    }

    public void setAppartements(Set<Appartement> appartements) {
        if (this.appartements != null) {
            this.appartements.forEach(i -> i.setResident(null));
        }
        if (appartements != null) {
            appartements.forEach(i -> i.setResident(this));
        }
        this.appartements = appartements;
    }

    public Resident appartements(Set<Appartement> appartements) {
        this.setAppartements(appartements);
        return this;
    }

    public Resident addAppartement(Appartement appartement) {
        this.appartements.add(appartement);
        appartement.setResident(this);
        return this;
    }

    public Resident removeAppartement(Appartement appartement) {
        this.appartements.remove(appartement);
        appartement.setResident(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resident)) {
            return false;
        }
        return id != null && id.equals(((Resident) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resident{" +
            "id=" + getId() +
            "}";
    }
}
