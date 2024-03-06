package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с изображениями.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}
