package com.syndicg5.repository;

import com.syndicg5.model.Appartement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AppartementRepository extends JpaRepository<Appartement, Long> {

    @Query("select a from Appartement a where a.immeuble.id = ?1")
    List<Appartement> findAppartementByImmeuble(long id);

    //Todo : Don't touch this one / create yours
    @Query("select a from Appartement a where a.immeuble.id = ?1  order by  a.numero")
    List<Appartement> findAllByImmeuble(long id);

    @Query("select a from Appartement a where a.immeuble.syndic.id = ?1 and a.resident.id = ?2 or a.resident is null order by a.immeuble.id, a.numero")
    List<Appartement> findAllByImmeubleResident(long id, long residentId);

    @Query("select a from Appartement a where a.immeuble.syndic.id = ?1")
    List<Appartement> findAllBySyndic(long id);

    @Query("select a from Appartement a where a.resident.id = ?1")
    List<Appartement> findAllByResident(long id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Appartement a set a.resident.id = ?1 where a.id = ?2")
    void updateAppartementResident(long residentId, long appartementId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Appartement a set a.resident.id = null where a.resident.id = ?1")
    void deleteAppartementResident(long residentId);
}
