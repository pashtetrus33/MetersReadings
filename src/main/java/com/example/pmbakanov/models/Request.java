package com.example.pmbakanov.models;

import com.example.pmbakanov.models.enums.ExecutorName;
import com.example.pmbakanov.models.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @ElementCollection(targetClass = Status.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "request_status",
            joinColumns = @JoinColumn(name = "request_id"))
    @Enumerated(EnumType.STRING)
    private Set<Status> statuses = new HashSet<>();
    private ExecutorName executor;
    private LocalDateTime dateOfCreated;

    public ExecutorName getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorName executor) {
        this.executor = executor;
    }

    public Set<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<Status> statuses) {
        this.statuses = statuses;
    }

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
    @PrePersist
    private void onCreate() {
        dateOfCreated = LocalDateTime.now().minusHours(3);
        statuses.add(Status.STATUS_NEW);
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "request")
    private List<Image> images = new ArrayList<>();

    public void addImageToRequest(Image image){
        image.setRequest(this);
        images.add(image);
    }
}
