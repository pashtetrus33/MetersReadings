package com.example.pmbakanov.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;;

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

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    private LocalDateTime dateOfCreated;

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
    private void onCreate() { dateOfCreated = LocalDateTime.now(); }


}
