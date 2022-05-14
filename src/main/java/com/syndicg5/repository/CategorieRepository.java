package com.syndicg5.repository;

import com.syndicg5.model.Categorie;
import com.syndicg5.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    @Query(" select distinct c from Categorie c " +
            " where c.syndic.id = ?1")
    List<Categorie> findAllBySyndic(long id);

    @Query(" select distinct c from Categorie c " +
            " where c.syndic.id = ?1 and c.libelle like %?2%")
    List<Categorie> findAllByLibelle(long syndicId, String libelle);
}
