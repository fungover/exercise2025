package com.bolaneradarmini.repository;

import com.bolaneradarmini.entity.AverageRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Gränssnittet hanterar SQL frågor automatiskt via JPA
@Repository
public interface AverageRateRepository extends JpaRepository<AverageRate, Long> {
}
