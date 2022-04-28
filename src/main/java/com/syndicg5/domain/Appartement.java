package com.syndicg5.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Appartement.
 */
@Entity
@Table(name = "appartement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Appartement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "etage")
    private Integer etage;

    @Column(name = "surface")
    private Double surface;

    @OneToMany(mappedBy = "appartement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "appartement" }, allowSetters = true)
    private Set<Payement> payements = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "appartements" }, allowSetters = true)
    private Resident resident;

    @ManyToOne
    @JsonIgnoreProperties(value = { "appartements", "syndic", "depenses", "revenus" }, allowSetters = true)
    private Immeuble immeuble;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Appartement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public Appartement numero(Integer numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getEtage() {
        return this.etage;
    }

    public Appartement etage(Integer etage) {
        this.setEtage(etage);
        return this;
    }

    public void setEtage(Integer etage) {
        this.etage = etage;
    }

    public Double getSurface() {
        return this.surface;
    }

    public Appartement surface(Double surface) {
        this.setSurface(surface);
        return this;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }

    public Set<Payement> getPayements() {
        return this.payements;
    }

    public void setPayements(Set<Payement> payements) {
        if (this.payements != null) {
            this.payements.forEach(i -> i.setAppartement(null));
        }
        if (payements != null) {
            payements.forEach(i -> i.setAppartement(this));
        }
        this.payements = payements;
    }

    public Appartement payements(Set<Payement> payements) {
        this.setPayements(payements);
        return this;
    }

    public Appartement addPayement(Payement payement) {
        this.payements.add(payement);
        payement.setAppartement(this);
        return this;
    }

    public Appartement removePayement(Payement payement) {
        this.payements.remove(payement);
        payement.setAppartement(null);
        return this;
    }

    public Resident getResident() {
        return this.resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public Appartement resident(Resident resident) {
        this.setResident(resident);
        return this;
    }

    public Immeuble getImmeuble() {
        return this.immeuble;
    }

    public void setImmeuble(Immeuble immeuble) {
        this.immeuble = immeuble;
    }

    public Appartement immeuble(Immeuble immeuble) {
        this.setImmeuble(immeuble);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appartement)) {
            return false;
        }
        return id != null && id.equals(((Appartement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Appartement{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", etage=" + getEtage() +
            ", surface=" + getSurface() +
            "}";
    }
}
