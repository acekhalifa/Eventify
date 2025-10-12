package com.eventify.repository;

import com.eventify.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Override
    Optional<Event> findById(Long aLong);

    @Query("SELECT e FROM Event e WHERE " +
            "LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.location) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Event> searchEvents(@Param("keyword") String keyword);

}