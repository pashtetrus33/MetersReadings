package com.example.pmbakanov.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Status {
    STATUS_NEW("Новая"), STATUS_IN_WORK("В работе"), STATUS_DONE("Выполнено");
    private final String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
