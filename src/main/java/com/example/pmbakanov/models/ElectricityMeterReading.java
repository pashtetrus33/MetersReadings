package com.example.pmbakanov.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.pmbakanov.controllers.UserController.TIME_SHIFT;

/**
 * Представляет собой запись счетчика электроэнергии.
 * Этот объект сопоставлен с таблицей "electricity_readings" в базе данных.
 */
@Entity
@Table(name = "electricity_readings")
@Data
public class ElectricityMeterReading implements Comparable<ElectricityMeterReading> {

    /**
     * Уникальный идентификатор записи о потреблении электроэнергии.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Количество электроэнергии, записанное в данной записи.
     */
    private Float electricity;

    /**
     * Пользователь, связанный с данной записью о потреблении электроэнергии.
     */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    /**
     * Дата и время создания данной записи.
     */
    private LocalDateTime dateOfCreated;

    /**
     * Форматированная строка, представляющая дату и время создания записи.
     */
    private String dateOfCreatedString;

    /**
     * Название поставщика данных для данной записи.
     */
    private String dataProviderName;

    /**
     * Сравнивает данную запись о потреблении электроэнергии с другой записью на основе адреса связанного пользователя.
     *
     * @param o Запись о потреблении электроэнергии, с которой производится сравнение.
     * @return Отрицательное целое число, ноль или положительное целое число в зависимости от того, меньше ли, равна ли или больше данная запись, чем указанная запись.
     */
    @Override
    public int compareTo(ElectricityMeterReading o) {
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

    /**
     * Проверяет, была ли данная запись сделана в текущем месяце.
     *
     * @return true, если запись была сделана в текущем месяце, в противном случае false.
     */
    public boolean doneInCurrentMonth() {
        LocalDateTime currentDate = LocalDateTime.now();
        return this.dateOfCreated.getYear() == currentDate.getYear() &&
                this.dateOfCreated.getMonth().equals(currentDate.getMonth());
    }
}
