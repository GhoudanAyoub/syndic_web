package com.syndicg5.repository;

import com.syndicg5.domain.Immeuble;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Immeuble entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImmeubleRepository extends MongoRepository<Immeuble, String> {}
