package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    User findByActivationCode(String code);
}

