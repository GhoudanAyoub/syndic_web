package com.syndicg5.repository;

import com.syndicg5.model.Immeuble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImmeubleRepository extends JpaRepository<Immeuble, Long> {
}
