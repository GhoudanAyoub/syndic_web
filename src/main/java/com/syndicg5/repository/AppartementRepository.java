package com.syndicg5.repository;

import com.syndicg5.model.Appartement;
import com.syndicg5.model.Depense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppartementRepository extends JpaRepository<Appartement, Long> {

    @Query(" select a from Appartement a where a.immeuble.id = ?1")
    List<Appartement> findAppartementByImmeuble(long id);
}
