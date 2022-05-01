package com.syndicg5.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "immeuble")
public class Immeuble implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "nom")
    private String nom;

    @Column(name = "etages")
    private Integer etages;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "ville")
    private String ville;

    @Lob
    @Column(name = "photo")
    private String photo;

    @OneToMany(mappedBy = "immeuble")
    private Set<Appartement> appartements = new HashSet<>();

    @ManyToOne
    private Syndic syndic;

    @OneToMany(mappedBy = "immeuble")
    private Set<Depense> depenses = new HashSet<>();

    @OneToMany(mappedBy = "immeuble")
    private Set<Revenu> revenus = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getEtages() {
        return etages;
    }

    public void setEtages(Integer etages) {
        this.etages = etages;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Set<Appartement> getAppartements() {
        return appartements;
    }

    public void setAppartements(Set<Appartement> appartements) {
        this.appartements = appartements;
    }

    public Syndic getSyndic() {
        return syndic;
    }

    public void setSyndic(Syndic syndic) {
        this.syndic = syndic;
    }

    public Set<Depense> getDepenses() {
        return depenses;
    }

    public void setDepenses(Set<Depense> depenses) {
        this.depenses = depenses;
    }

    public Set<Revenu> getRevenus() {
        return revenus;
    }

    public void setRevenus(Set<Revenu> revenus) {
        this.revenus = revenus;
    }
}
