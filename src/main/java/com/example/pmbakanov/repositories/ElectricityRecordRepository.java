package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.ElectricityRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectricityRecordRepository extends JpaRepository<ElectricityRecord, Long> {
    List<ElectricityRecord> findById(String id);
}
