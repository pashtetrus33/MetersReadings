package com.example.pmbakanov.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Представляет изображение.
 * Этот объект сопоставлен с таблицей "images" в базе данных.
 */
@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Image {

    /** Уникальный идентификатор изображения. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /** Название изображения. */
    @Column(name = "name")
    private String name;

    /** Оригинальное имя файла изображения. */
    @Column(name = "original_file_name")
    private String originalFileName;

    /** Размер изображения. */
    @Column(name = "size")
    private Long size;

    /** Тип содержимого изображения. */
    @Column(name = "content_type")
    private String contentType;

    /** Байтовое представление изображения. */
    @Lob
    private byte[] bytes;

    /** Запрос, связанный с изображением. */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Request request;
}
