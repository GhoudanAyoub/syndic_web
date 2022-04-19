package com.syndicg5.domain;

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

    @Field("ville")
    private String ville;

    @Field("photo")
    private String photo;

    @Field("tel")
    private String tel;

    @DBRef
    @Field("internalUser")
    private User internalUser;

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

    public String getVille() {
        return this.ville;
    }

    public Personne ville(String ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPhoto() {
        return this.photo;
    }

    public Personne photo(String photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTel() {
        return this.tel;
    }

    public Personne tel(String tel) {
        this.setTel(tel);
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public Personne internalUser(User user) {
        this.setInternalUser(user);
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
            ", ville='" + getVille() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", tel='" + getTel() + "'" +
            "}";
    }
}
