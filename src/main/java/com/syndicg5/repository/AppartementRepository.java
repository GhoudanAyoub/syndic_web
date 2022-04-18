package com.syndicg5.repository;

import com.syndicg5.domain.Appartement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Appartement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppartementRepository extends MongoRepository<Appartement, String> {}
