package com.example.pmbakanov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс для запуска приложения по считыванию показаний счетчиков.
 */
@SpringBootApplication
public class MetersReadingsApplication {

    /**
     * Главный метод для запуска приложения по считыванию показаний счетчиков.
     *
     * @param args Аргументы командной строки.
     */
    public static void main(String[] args) {
        SpringApplication.run(MetersReadingsApplication.class, args);
    }
}