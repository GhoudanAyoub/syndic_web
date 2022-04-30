package com.syndicg5.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Syndic.
 */
@Entity
@Table(name = "syndic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Syndic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "tel")
    private String tel;

    @Column(name = "date_travail")
    private LocalDate dateTravail;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(unique = true, name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "syndic")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "appartements", "syndic", "depenses", "revenus" }, allowSetters = true)
    private Set<Immeuble> immeubles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Syndic id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Syndic adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return this.tel;
    }

    public Syndic tel(String tel) {
        this.setTel(tel);
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public LocalDate getDateTravail() {
        return this.dateTravail;
    }

    public Syndic dateTravail(LocalDate dateTravail) {
        this.setDateTravail(dateTravail);
        return this;
    }

    public void setDateTravail(LocalDate dateTravail) {
        this.dateTravail = dateTravail;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Syndic photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Syndic photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Syndic user(User user) {
        this.setUser(user);
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
            ", adresse='" + getAdresse() + "'" +
            ", tel='" + getTel() + "'" +
            ", dateTravail='" + getDateTravail() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
