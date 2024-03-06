package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.ElectricityMeterReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с записями о потреблении электроэнергии.
 */
public interface ElectricityMeterReadingRepository extends JpaRepository<ElectricityMeterReading, Long> {

    /**
     * Находит записи о потреблении электроэнергии по идентификатору.
     * @param id Идентификатор записей о потреблении электроэнергии.
     * @return Список записей о потреблении электроэнергии с указанным идентификатором.
     */
    List<ElectricityMeterReading> findById(String id);
}
