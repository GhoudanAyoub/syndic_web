package com.syndicg5.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Payement.
 */
@Document(collection = "payement")
public class Payement implements Serializable {

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
    @Field("appartement")
    @JsonIgnoreProperties(value = { "payements", "resident", "immeuble" }, allowSetters = true)
    private Appartement appartement;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Payement id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getMontant() {
        return this.montant;
    }

    public Payement montant(Double montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Payement date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public Payement description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Appartement getAppartement() {
        return this.appartement;
    }

    public void setAppartement(Appartement appartement) {
        this.appartement = appartement;
    }

    public Payement appartement(Appartement appartement) {
        this.setAppartement(appartement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payement)) {
            return false;
        }
        return id != null && id.equals(((Payement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payement{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
