package com.syndicg5.repository;

import com.syndicg5.domain.Syndic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Syndic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SyndicRepository extends MongoRepository<Syndic, String> {}
