package com.syndicg5.repository;

import com.syndicg5.model.Syndic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SyndicRepository extends JpaRepository<Syndic, Long> {
    @Query("select s from Syndic s where s.email=:email")
    Syndic findByEmail(String email);
}
