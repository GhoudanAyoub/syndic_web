package com.syndicg5.repository;

import com.syndicg5.domain.Revenu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Revenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RevenuRepository extends JpaRepository<Revenu, Long> {}
