package com.syndicg5.repository;

import com.syndicg5.domain.Revenu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Revenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RevenuRepository extends MongoRepository<Revenu, String> {}
