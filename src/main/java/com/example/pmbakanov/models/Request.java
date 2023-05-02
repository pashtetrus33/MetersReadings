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

@Entity
@Table(name = "requests")
@Data
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Status status;
    private ExecutorName executor;
    private LocalDateTime dateOfCreated;
    private String dateOfCreatedString;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "request")
    private List<Image> images = new ArrayList<>();

    @PrePersist
    private void onCreate() {
        dateOfCreated = LocalDateTime.now().minusHours(TIME_SHIFT);
        status = Status.STATUS_NEW;
        dateOfCreatedString = dateOfCreated.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public void addImageToRequest(Image image) {
        image.setRequest(this);
        images.add(image);
    }
}
