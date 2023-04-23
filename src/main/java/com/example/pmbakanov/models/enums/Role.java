package com.example.pmbakanov.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_SUPERVISOR, ROLE_BUH, ROLE_TECHNICIAN, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
