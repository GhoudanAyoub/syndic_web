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
}
