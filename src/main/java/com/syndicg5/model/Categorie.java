package com.syndicg5.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "categorie")
public class Categorie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @ManyToOne
    private Depense depense;

    @ManyToOne
    private Revenu revenu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Depense getDepense() {
        return depense;
    }

    public void setDepense(Depense depense) {
        this.depense = depense;
    }

    public Revenu getRevenu() {
        return revenu;
    }

    public void setRevenu(Revenu revenu) {
        this.revenu = revenu;
    }
}
