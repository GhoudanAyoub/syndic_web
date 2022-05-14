package com.syndicg5.repository;

import com.syndicg5.model.Depense;
import com.syndicg5.model.Revenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepenseRepository extends JpaRepository<Depense, Long> {

    @Query(" select d from Depense d where d.immeuble.id = ?1")
    List<Depense> findDepensesByImmeuble(long id);

    @Query(" select d from Depense d where d.immeuble.syndic.id = ?1")
    List<Depense> findDepensesBySyndic(long id);
}
