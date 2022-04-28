package com.syndicg5.repository;

import com.syndicg5.domain.Resident;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Resident entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {}
