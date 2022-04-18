package com.syndicg5.repository;

import com.syndicg5.domain.Depense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Depense entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepenseRepository extends MongoRepository<Depense, String> {}
