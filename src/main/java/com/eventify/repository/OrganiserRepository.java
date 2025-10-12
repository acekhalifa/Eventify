package com.eventify.repository;

import com.eventify.entity.Organiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganiserRepository extends JpaRepository<Organiser, Long> {

    Optional<Organiser> findByEmail(String email);

    boolean existsByEmail(String email);
}
