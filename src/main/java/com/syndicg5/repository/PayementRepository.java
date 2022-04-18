package com.syndicg5.repository;

import com.syndicg5.domain.Payement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Payement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayementRepository extends MongoRepository<Payement, String> {}
