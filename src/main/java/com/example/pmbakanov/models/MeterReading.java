package com.example.pmbakanov.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.pmbakanov.controllers.UserController.TIME_SHIFT;

/**
 * Представляет показания счетчика.
 * Этот объект сопоставлен с таблицей "readings" в базе данных.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "readings")
@Data
public class MeterReading implements Comparable<MeterReading> {

    /** Уникальный идентификатор показаний счетчика. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Показания счетчика холодной воды на кухне. */
    private Float kitchenCold;

    /** Показания счетчика горячей воды на кухне. */
    private Float kitchenHot;

    /** Показания счетчика холодной воды в туалете. */
    private Float toiletCold;

    /** Показания счетчика горячей воды в туалете. */
    private Float toiletHot;

    /** Показания счетчика горячей воды у соседа. */
    private Float neighborHot;

    /** Показания счетчика холодной воды у соседа. */
    private Float neighborCold;

    /** Пользователь, связанный с показаниями счетчика. */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    /** Дата и время создания показаний счетчика. */
    private LocalDateTime dateOfCreated;

    /** Форматированная строка, представляющая дату и время создания показаний. */
    private String dateOfCreatedString;

    /**
     * Сравнивает показания счетчика с другими на основе адреса связанного пользователя.
     * @param o Показания счетчика для сравнения.
     * @return Отрицательное целое число, ноль или положительное целое число в зависимости от того, меньше ли, равны ли или больше данная показания, чем указанные показания.
     */
    @Override
    public int compareTo(MeterReading o) {
        return this.getUser().getAddress().compareTo(o.getUser().getAddress());
    }

    /**
     * Устанавливает дату и время создания перед сохранением сущности.
     */
    @PrePersist
    private void onCreate() {
        dateOfCreated = LocalDateTime.now().minusHours(TIME_SHIFT);
        dateOfCreatedString = dateOfCreated.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
