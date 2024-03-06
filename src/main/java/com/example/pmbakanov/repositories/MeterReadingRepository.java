package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с показаниями счетчика.
 */
public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {

    /**
     * Находит показания счетчика по их идентификатору.
     * @param id Идентификатор показаний счетчика.
     * @return Список показаний счетчика с указанным идентификатором.
     */
    List<MeterReading> findById(String id);
}