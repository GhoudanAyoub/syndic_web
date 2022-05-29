package com.syndicg5.repository;

import com.syndicg5.model.Immeuble;
import com.syndicg5.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
        @Query(" select distinct r from Resident r " +
                        " where r.syndic.id = ?1")
        List<Resident> findAllBySyndic(long id);

        @Query(" select distinct r from Resident r " +
                        " where r.syndic.id = ?1 and r.nom like %?2%")
        List<Resident> findAllByNom(long syndicId, String nom);

        @Query(" select s from Resident s " +
                        " where s.email = ?1")
        Resident findByEmail(String email);

        @Query(" select count(s.id) from Resident s ")
        Integer nombreResident();

        @Query("select s from Resident s where s.email = ?1 and s.mdp = ?2")
        Resident check(String email,String mdp);
}
