package exercise8.repository;

import exercise8.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Find events by a specific date
    List<Event> findByStartDateAfter(LocalDate date);

    // Find events before a certain date
    List<Event> findByStartDateBefore(LocalDate date);

    // Search for events with names that contain text
    List<Event> findByNameContainingIgnoreCase(String name);

    // Find ongoing events (between start and end dates)
    List<Event> findByStartDateBeforeAndEndDateAfter(LocalDate startDate, LocalDate endDate);

}
