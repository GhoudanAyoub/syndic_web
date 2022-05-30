package com.syndicg5.repository;

import com.syndicg5.model.Revenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenuRepository extends JpaRepository<Revenu, Long> {

    @Query(" select r from Revenu r where r.immeuble.id = ?1")
    List<Revenu> findRevenusByImmeuble(long id);

    @Query(" select r from Revenu r where r.appartement.id = ?1")
    List<Revenu> findRevenusByAppartement(long id);

    @Query(" select r from Revenu r where r.immeuble.syndic.id = ?1")
    List<Revenu> findRevenusBySyndic(long id);

    @Query(" select max(montant) from Revenu ")
    double findRevenueMax();

    @Query("select  Year(r.date) from Revenu r group by Year(r.date)  ")
    List<Object[]> revenuParAnnee();

    @Query("select sum(r.montant) from Revenu r group by Year(r.date)  ")
    List<Object[]> revenuParMontant();

    //Todo lghoudan
    @Query(" select r from Revenu r where r.appartement.resident.id = ?1")
    List<Revenu> findRevenusByResident(long id);

    @Query("select distinct Year(r.date) from Revenu r where r.appartement.id in (select a from Appartement a where a.resident.id=?1 )")
    List<Integer> findRevenuDates(long id);

    @Query("select r.appartement.numero, Month(r.date), sum(r.montant) from Revenu r where r.appartement.id in (select a from Appartement a where a.resident.id=?1 ) and Year(r.date) = ?2 group by r.appartement.numero, Month(r.date)")
    List<Object[]> findRevenusAppartement(long id, int year);

}
