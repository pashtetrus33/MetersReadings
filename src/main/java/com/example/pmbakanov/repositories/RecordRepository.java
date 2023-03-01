package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findById(String id);
}
