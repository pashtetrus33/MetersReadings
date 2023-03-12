package com.example.pmbakanov.models;

import com.example.pmbakanov.models.enums.Role;
import com.example.pmbakanov.models.enums.SpecialAdresses;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails, Comparable<User> {
    public Long getId() {
        return id;
    }

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

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<Record> records = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Request> requests = new ArrayList<>();

    public void addRecordToUser(Record record) {
        record.setUser(this);
        records.add(record);
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public Record getLastRecord() {
        Record result = null;
        if (areRecords())
            result = records.get(records.size() - 1);

        return result;
    }

    public boolean areRecords() {
        return records.size() > 0;
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

    public void addRequestToUser(Request request) {
        request.setUser(this);
        requests.add(request);
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
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
        for (SpecialAdresses adress : SpecialAdresses.values()) {
            if (adress.getTitle().equals(this.address))
                return true;
        }
        return false;
    }
}
