package com.example.pmbakanov.models;

import com.example.pmbakanov.models.enums.ExecutorName;
import com.example.pmbakanov.models.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    private Status status;
    private ExecutorName executor;
    private LocalDateTime dateOfCreated;

    private String dateOfCreatedString;

    public ExecutorName getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorName executor) {
        this.executor = executor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
    @PrePersist
    private void onCreate() {
        dateOfCreated = LocalDateTime.now().minusHours(2);
        status = Status.STATUS_NEW;
        dateOfCreatedString = dateOfCreated.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public String getDateOfCreatedString() {
        return dateOfCreatedString;
    }

    public void setDateOfCreatedString(String dateOfCreatedString) {
        this.dateOfCreatedString = dateOfCreatedString;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "request")
    private List<Image> images = new ArrayList<>();

    public void addImageToRequest(Image image){
        image.setRequest(this);
        images.add(image);
    }
}
