package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collections;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByActivationCode(String code);
    User findByEmail(String email);
    User findByAddress(String address);
}

