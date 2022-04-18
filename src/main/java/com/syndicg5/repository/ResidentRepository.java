package com.syndicg5.repository;

import com.syndicg5.domain.Resident;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Resident entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResidentRepository extends MongoRepository<Resident, String> {}
