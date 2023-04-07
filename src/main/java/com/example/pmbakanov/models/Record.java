package com.example.pmbakanov.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;;

@Entity
@Table(name = "records")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer kitchenCold;
    private Integer kitchenHot;
    private Integer toiletCold;
    private Integer toiletHot;
    private Integer neighborHot;

    public Integer getNeighborHot() {
        return neighborHot;
    }

    public void setNeighborHot(Integer neighborHot) {
        this.neighborHot = neighborHot;
    }

    public Integer getNeighborCold() {
        return neighborCold;
    }

    public void setNeighborCold(Integer neighborCold) {
        this.neighborCold = neighborCold;
    }

    private Integer neighborCold;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    private LocalDateTime dateOfCreated;

    private String dateOfCreatedString;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKitchenCold() {
        return kitchenCold;
    }

    public void setKitchenCold(Integer kitchenCold) {
        this.kitchenCold = kitchenCold;
    }

    public Integer getKitchenHot() {
        return kitchenHot;
    }

    public void setKitchenHot(Integer kitchenHot) {
        this.kitchenHot = kitchenHot;
    }

    public Integer getToiletCold() {
        return toiletCold;
    }

    public void setToiletCold(Integer toiletCold) {
        this.toiletCold = toiletCold;
    }

    public Integer getToiletHot() {
        return toiletHot;
    }

    public void setToiletHot(Integer toiletHot) {
        this.toiletHot = toiletHot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }


    @PrePersist
    private void onCreate() {
        dateOfCreated = LocalDateTime.now().minusHours(4);
        dateOfCreatedString = dateOfCreated.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public String getDateOfCreatedString() {
        return dateOfCreatedString;
    }

    public void setDateOfCreatedString(String dateOfCreatedString) {
        this.dateOfCreatedString = dateOfCreatedString;
    }
}
