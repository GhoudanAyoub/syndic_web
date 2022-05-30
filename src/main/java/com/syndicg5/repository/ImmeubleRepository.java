package com.syndicg5.repository;

import com.syndicg5.model.Immeuble;
import com.syndicg5.model.Resident;
import com.syndicg5.model.Revenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface ImmeubleRepository extends JpaRepository<Immeuble, Long> {
        @Query(" select i from Immeuble i " +
                        " where i.syndic.id = ?1")
        List<Immeuble> findAllBySyndic(long id);

        @Query(" select distinct i from Immeuble i " +
                        " where i.syndic.id = ?1 and i.nom like %?2%")
        List<Immeuble> findAllByNom(long syndicId, String nom);

        @Query(" select Count(i.id) from Immeuble i ")
        Integer nomreImmeuble();

        @Query(" select i from Immeuble i, Appartement a " +
                " where a.immeuble.id = i.id and a.resident.email = ?1")
        List<Immeuble> findAllByResident(String email);

        @Query("select distinct Year(r.date) from Revenu r where r.immeuble.id = ?1")
        List<Integer> findRevenuDates(long id);

        @Query("select distinct Year(d.date) from Depense d where d.immeuble.id = ?1")
        List<Integer> findDepenseDates(long id);

        @Query("select r.appartement.numero, Month(r.date), sum(r.montant) from Revenu r where r.immeuble.id = ?1 and Year(r.date) = ?2 group by r.appartement.numero, Month(r.date)")
        List<Object[]> findRevenusImmeuble(long id, int year);

        @Query("select d.categorie.libelle, Month(d.date), sum(d.montant) from Depense d where d.immeuble.id = ?1 and Year(d.date) = ?2 group by d.categorie.libelle, Month(d.date)")
        List<Object[]> findDepensesImmeuble(long id, int year);

        @Query(" select distinct i.immeuble from Appartement i " +
                " where i.resident.id = ?1")
        List<Immeuble> findAllResident(long id);

}
