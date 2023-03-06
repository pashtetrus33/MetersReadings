package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findById(String id);

}
