package com.syndicg5.repository;

import com.syndicg5.model.Immeuble;
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
}
