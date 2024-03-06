package com.example.pmbakanov.models.enums;

/**
 * Перечисление SpecialAdresses содержит специальные адреса, где в квартирах находятся счетчики соседа.
 * Каждый адрес представлен в виде строки и связан с адресом соседа.
 */
public enum SpecialAdresses {
    //Адреса, где в квартирах находятся счетчики соседа (холодная и горячая вода на кухне)
    HOUSE_2_10("Жилой дом №2 кв.№10","Жилой дом №2 кв.№11"),
    HOUSE_2_25("Жилой дом №2 кв.№25","Жилой дом №2 кв.№24"),
    HOUSE_2_31("Жилой дом №2 кв.№31","Жилой дом №2 кв.№32"),
    HOUSE_2_34("Жилой дом №2 кв.№34","Жилой дом №2 кв.№33"),
    HOUSE_2_41("Жилой дом №2 кв.№41","Жилой дом №2 кв.№42"),
    HOUSE_2_44("Жилой дом №2 кв.№44","Жилой дом №2 кв.№43"),
    HOUSE_2_51("Жилой дом №2 кв.№51","Жилой дом №2 кв.№52"),
    HOUSE_2_54("Жилой дом №2 кв.№54","Жилой дом №2 кв.№53");
    private final String title;
    private final String neighborAddress;

    /**
     * Создает новый экземпляр перечисления SpecialAdresses с указанным адресом и адресом соседа.
     *
     * @param title           адрес квартиры
     * @param neighborAddress адрес соседа
     */
    SpecialAdresses(String title, String neighborAddress) {
        this.title = title;
        this.neighborAddress = neighborAddress;
    }

    /**
     * Получает адрес квартиры.
     *
     * @return адрес квартиры
     */
    public String getTitle() {
        return title;
    }

    /**
     * Получает адрес соседа.
     *
     * @return адрес соседа
     */
    public String getNeighborAddress() {
        return neighborAddress;
    }

    /**
     * Возвращает строковое представление адреса квартиры.
     *
     * @return строковое представление адреса квартиры
     */
    @Override
    public String toString() {
        return title;
    }
}
