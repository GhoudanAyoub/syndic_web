package com.syndicg5.repository;

import com.syndicg5.model.Immeuble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImmeubleRepository extends JpaRepository<Immeuble, Long> {
    @Query(" select i from Immeuble i " +
            " where i.syndic.id = ?1")
    List<Immeuble> findAllBySyndic(long id);
}
