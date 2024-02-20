package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {
    List<MeterReading> findById(String id);
}
