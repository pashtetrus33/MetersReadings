package com.example.pmbakanov.models.enums;

public enum Executor {
    ORLOV("Орлов А."), ZHUKOV("Жуков Д."), SHAYAKHMETOV("Шаяхметов М."), GLUSHKOV("Глушков А.");
    private final String title;

    Executor(String title) {
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
