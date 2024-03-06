package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с запросами.
 */
public interface RequestRepository extends JpaRepository<Request, Long> {

    /**
     * Находит запросы по их идентификатору.
     * @param id Идентификатор запросов.
     * @return Список запросов с указанным идентификатором.
     */
    List<Request> findById(String id);
}