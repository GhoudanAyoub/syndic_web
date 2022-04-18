package com.syndicg5.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Personne.
 */
@Document(collection = "personne")
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
    private String nom;

    @Field("prenom")
    private String prenom;

    @Field("email")
    private String email;

    @Field("mot_passe")
    private String motPasse;

    @Field("adresse")
    private String adresse;

    @DBRef
    private Resident resident;

    @DBRef
    private Syndic syndic;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Personne id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Personne nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Personne prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return this.email;
    }

    public Personne email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotPasse() {
        return this.motPasse;
    }

    public Personne motPasse(String motPasse) {
        this.setMotPasse(motPasse);
        return this;
    }

    public void setMotPasse(String motPasse) {
        this.motPasse = motPasse;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Personne adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Resident getResident() {
        return this.resident;
    }

    public void setResident(Resident resident) {
        if (this.resident != null) {
            this.resident.setPersonne(null);
        }
        if (resident != null) {
            resident.setPersonne(this);
        }
        this.resident = resident;
    }

    public Personne resident(Resident resident) {
        this.setResident(resident);
        return this;
    }

    public Syndic getSyndic() {
        return this.syndic;
    }

    public void setSyndic(Syndic syndic) {
        if (this.syndic != null) {
            this.syndic.setPersonne(null);
        }
        if (syndic != null) {
            syndic.setPersonne(this);
        }
        this.syndic = syndic;
    }

    public Personne syndic(Syndic syndic) {
        this.setSyndic(syndic);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Personne)) {
            return false;
        }
        return id != null && id.equals(((Personne) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Personne{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", email='" + getEmail() + "'" +
            ", motPasse='" + getMotPasse() + "'" +
            ", adresse='" + getAdresse() + "'" +
            "}";
    }
}
