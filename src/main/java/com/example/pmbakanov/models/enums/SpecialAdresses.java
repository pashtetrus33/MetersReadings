package com.example.pmbakanov.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum SpecialAdresses {

    HOUSE_2_41("Жилой дом №2 кв.№41"), HOUSE_2_44("Жилой дом №2 кв.№44"),
    HOUSE_2_51("Жилой дом №2 кв.№51"), HOUSE_2_54("Жилой дом №2 кв.№54");
    private final String title;

    SpecialAdresses(String title) {
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
