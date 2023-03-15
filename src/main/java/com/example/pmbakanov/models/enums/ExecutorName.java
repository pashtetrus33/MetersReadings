package com.example.pmbakanov.models.enums;

public enum ExecutorName {
    NEW("Не назначен"), ORLOV("Орлов А."), ZHUKOV("Жуков Д."), SHAYAKHMETOV("Шаяхметов М."), GLUSHKOV("Глушков А.");
    private final String title;

    ExecutorName(String title) {
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
