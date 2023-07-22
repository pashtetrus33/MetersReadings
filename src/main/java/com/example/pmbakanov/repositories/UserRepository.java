package com.example.pmbakanov.repositories;

import com.example.pmbakanov.models.User;
import com.example.pmbakanov.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByActivationCode(String code);
    User findByEmail(String email);
    User findByAddress(String address);
    List<User>  findAllByActiveIsTrue();

    List<User> findAllByRoles(Role role);
}

