package exercise8.repository;

import exercise8.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Hitta events efter ett visst datum
    List<Event> findByStartDateAfter(LocalDate date);

    // Hitta events före ett visst datum
    List<Event> findByStartDateBefore(LocalDate date);

    // Sök events med namn som innehåller text
    List<Event> findByNameContainingIgnoreCase(String name);

    // Hitta pågående events (mellan start och slutdatum)
    List<Event> findByStartDateBeforeAndEndDateAfter(LocalDate endDate, LocalDate startDate);
}
