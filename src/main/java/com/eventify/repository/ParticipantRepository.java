package com.eventify.repository;

import com.eventify.entity.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Page<Participant> findByEventId(Long eventId, Pageable pageable);

    Optional<Participant> findByEventIdAndEmail(Long eventId, String email);

    Optional<Participant> findByIdAndEventId(Long id, Long eventId);

    List<Participant> findByEventId(Long eventId);

    boolean existsByEventIdAndEmail(Long eventId, String email);
}
