package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.ElectricityMeterReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectricityMeterReadingRepository extends JpaRepository<ElectricityMeterReading, Long> {
    List<ElectricityMeterReading> findById(String id);
}
