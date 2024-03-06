package com.example.pmbakanov.models;

import com.example.pmbakanov.models.enums.ExecutorName;
import com.example.pmbakanov.models.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.pmbakanov.controllers.UserController.TIME_SHIFT;

/**
 * Представляет запрос.
 * Этот объект сопоставлен с таблицей "requests" в базе данных.
 */
@Entity
@Table(name = "requests")
@Data
public class Request {

    /** Уникальный идентификатор запроса. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Описание запроса. */
    private String description;

    /** Статус запроса. */
    private Status status;

    /** Исполнитель запроса. */
    private ExecutorName executor;

    /** Дата и время создания запроса. */
    private LocalDateTime dateOfCreated;

    /** Форматированная строка, представляющая дату и время создания запроса. */
    private String dateOfCreatedString;

    /** Пользователь, связанный с запросом. */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    /** Список изображений, связанных с запросом. */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "request")
    private List<Image> images = new ArrayList<>();

    /**
     * Устанавливает дату и время создания и статус по умолчанию перед сохранением сущности.
     */
    @PrePersist
    private void onCreate() {
        dateOfCreated = LocalDateTime.now().minusHours(TIME_SHIFT);
        status = Status.STATUS_NEW;
        dateOfCreatedString = dateOfCreated.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    /**
     * Добавляет изображение к запросу и устанавливает соответствующую связь.
     * @param image Изображение для добавления к запросу.
     */
    public void addImageToRequest(Image image) {
        image.setRequest(this);
        images.add(image);
    }
}
