package com.syndicg5.repository;

import com.syndicg5.domain.Appartement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Appartement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppartementRepository extends JpaRepository<Appartement, Long> {}
