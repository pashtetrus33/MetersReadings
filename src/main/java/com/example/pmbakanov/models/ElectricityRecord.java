package com.example.pmbakanov.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.pmbakanov.controllers.UserController.TIME_SHIFT;

@Entity
@Table(name = "electricity_records")
@Data
public class ElectricityRecord implements Comparable<ElectricityRecord> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float electricity;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
    private LocalDateTime dateOfCreated;
    private String dateOfCreatedString;

    @Override
    public int compareTo(ElectricityRecord o) {
        return this.getUser().getAddress().compareTo(o.getUser().getAddress());
    }

    @PrePersist
    private void onCreate() {
        dateOfCreated = LocalDateTime.now().minusHours(TIME_SHIFT);
        dateOfCreatedString = dateOfCreated.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public boolean doneInCurrentMonth() {
        return this.dateOfCreated.getMonth().equals(LocalDateTime.now().getMonth());
    }
}
