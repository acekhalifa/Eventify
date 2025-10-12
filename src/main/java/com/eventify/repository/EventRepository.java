package com.eventify.repository;

import com.eventify.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByOrganiserId(Long id, Pageable page);

    Optional<Event> findByIdAndOrganiserId(Long id, Long organiserId);

    @Query("SELECT e FROM Event e WHERE e.organiser.id = :organiserId AND" +
            "LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.location) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Event> searchEvents(@Param("organiserId") Long organiserId, @Param("keyword") String keyword,
                             Pageable pageable);

}