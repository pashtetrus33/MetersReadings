package com.example.pmbakanov.models;

import com.example.pmbakanov.models.enums.Role;
import com.example.pmbakanov.models.enums.SpecialAdresses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@Data
public class User implements UserDetails, Comparable<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String flat;
    private String name;
    private boolean active;
    @Column(length = 1000)
    private String password;
    @Column(unique = true, updatable = false)
    private String email;
    private String activationCode;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<MeterReading> meterReadings = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Request> requests = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<ElectricityRecord> electricityRecords = new ArrayList<>();

    public MeterReading getLastRecord() {
        return areRecords() ? meterReadings.get(meterReadings.size() - 1) : null;
    }

    public ElectricityRecord getLastElectricityRecord() {
        return areElectricityRecords() ? electricityRecords.get(electricityRecords.size() - 1) : null;
    }

    public boolean areRecords() {
        return meterReadings.size() > 0;
    }

    public boolean areElectricityRecords() {
        return electricityRecords.size() > 0;
    }

    public boolean areNeighborRecords() {
        return getLastRecord().getNeighborHot() != null;
    }

    public Request getFirstRequest() {
        Request result = null;
        if (areRequests())
            result = requests.get(0);
        return result;
    }

    public boolean areRequests() {
        return requests.size() > 0;
    }

    // security config
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public boolean isAdmin() {
        return roles.contains(Role.ROLE_ADMIN);
    }

    public boolean isTechnician() {
        return roles.contains(Role.ROLE_TECHNICIAN);
    }

    public boolean isSupervisor() {
        return roles.contains(Role.ROLE_SUPERVISOR);
    }

    public boolean isBuh() {
        return roles.contains(Role.ROLE_BUH);
    }

    @Override
    public int compareTo(User o) {
        return this.getAddress().compareTo(o.getAddress());
    }

    public boolean isUserWithSpecialAddress() {
        for (SpecialAdresses address : SpecialAdresses.values()) {
            if (address.getTitle().equals(this.address))
                return true;
        }
        return false;
    }

    public boolean isUserWithReducedData() {
        for (SpecialAdresses address : SpecialAdresses.values()) {
            if (address.getNeighborAddress().equals(this.address))
                return true;
        }
        return false;
    }
}
