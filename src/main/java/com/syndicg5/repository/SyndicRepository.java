package com.syndicg5.repository;

import com.syndicg5.domain.Syndic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Syndic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SyndicRepository extends JpaRepository<Syndic, Long> {}
