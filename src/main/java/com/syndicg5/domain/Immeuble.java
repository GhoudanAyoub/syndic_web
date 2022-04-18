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
 * A Immeuble.
 */
@Document(collection = "immeuble")
public class Immeuble implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("libelle")
    private String libelle;

    @Field("adresse")
    private String adresse;

    @Field("ville")
    private String ville;

    @Field("numero")
    private Integer numero;

    @Field("nb_etages")
    private Integer nbEtages;

    @DBRef
    @Field("appartement")
    @JsonIgnoreProperties(value = { "payements", "resident", "immeuble" }, allowSetters = true)
    private Set<Appartement> appartements = new HashSet<>();

    @DBRef
    @Field("syndic")
    @JsonIgnoreProperties(value = { "personne", "immeubles" }, allowSetters = true)
    private Syndic syndic;

    @DBRef
    @Field("depense")
    @JsonIgnoreProperties(value = { "categories", "immeuble" }, allowSetters = true)
    private Set<Depense> depenses = new HashSet<>();

    @DBRef
    @Field("revenu")
    @JsonIgnoreProperties(value = { "categories", "immeuble" }, allowSetters = true)
    private Set<Revenu> revenus = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Immeuble id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Immeuble libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Immeuble adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return this.ville;
    }

    public Immeuble ville(String ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public Immeuble numero(Integer numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getNbEtages() {
        return this.nbEtages;
    }

    public Immeuble nbEtages(Integer nbEtages) {
        this.setNbEtages(nbEtages);
        return this;
    }

    public void setNbEtages(Integer nbEtages) {
        this.nbEtages = nbEtages;
    }

    public Set<Appartement> getAppartements() {
        return this.appartements;
    }

    public void setAppartements(Set<Appartement> appartements) {
        if (this.appartements != null) {
            this.appartements.forEach(i -> i.setImmeuble(null));
        }
        if (appartements != null) {
            appartements.forEach(i -> i.setImmeuble(this));
        }
        this.appartements = appartements;
    }

    public Immeuble appartements(Set<Appartement> appartements) {
        this.setAppartements(appartements);
        return this;
    }

    public Immeuble addAppartement(Appartement appartement) {
        this.appartements.add(appartement);
        appartement.setImmeuble(this);
        return this;
    }

    public Immeuble removeAppartement(Appartement appartement) {
        this.appartements.remove(appartement);
        appartement.setImmeuble(null);
        return this;
    }

    public Syndic getSyndic() {
        return this.syndic;
    }

    public void setSyndic(Syndic syndic) {
        this.syndic = syndic;
    }

    public Immeuble syndic(Syndic syndic) {
        this.setSyndic(syndic);
        return this;
    }

    public Set<Depense> getDepenses() {
        return this.depenses;
    }

    public void setDepenses(Set<Depense> depenses) {
        if (this.depenses != null) {
            this.depenses.forEach(i -> i.setImmeuble(null));
        }
        if (depenses != null) {
            depenses.forEach(i -> i.setImmeuble(this));
        }
        this.depenses = depenses;
    }

    public Immeuble depenses(Set<Depense> depenses) {
        this.setDepenses(depenses);
        return this;
    }

    public Immeuble addDepense(Depense depense) {
        this.depenses.add(depense);
        depense.setImmeuble(this);
        return this;
    }

    public Immeuble removeDepense(Depense depense) {
        this.depenses.remove(depense);
        depense.setImmeuble(null);
        return this;
    }

    public Set<Revenu> getRevenus() {
        return this.revenus;
    }

    public void setRevenus(Set<Revenu> revenus) {
        if (this.revenus != null) {
            this.revenus.forEach(i -> i.setImmeuble(null));
        }
        if (revenus != null) {
            revenus.forEach(i -> i.setImmeuble(this));
        }
        this.revenus = revenus;
    }

    public Immeuble revenus(Set<Revenu> revenus) {
        this.setRevenus(revenus);
        return this;
    }

    public Immeuble addRevenu(Revenu revenu) {
        this.revenus.add(revenu);
        revenu.setImmeuble(this);
        return this;
    }

    public Immeuble removeRevenu(Revenu revenu) {
        this.revenus.remove(revenu);
        revenu.setImmeuble(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Immeuble)) {
            return false;
        }
        return id != null && id.equals(((Immeuble) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Immeuble{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", ville='" + getVille() + "'" +
            ", numero=" + getNumero() +
            ", nbEtages=" + getNbEtages() +
            "}";
    }
}
