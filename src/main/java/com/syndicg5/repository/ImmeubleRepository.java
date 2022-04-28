package com.syndicg5.repository;

import com.syndicg5.domain.Immeuble;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Immeuble entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImmeubleRepository extends JpaRepository<Immeuble, Long> {}
