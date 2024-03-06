package com.example.pmbakanov.models.enums;

/**
 * Перечисление Status представляет статусы задач в системе.
 * Каждый статус связан с определенным состоянием задачи и представлен в виде строки.
 */
public enum Status {
    STATUS_NEW("Новая"), STATUS_IN_WORK("В работе"), STATUS_DONE("Выполнено");
    private final String title;

    /**
     * Создает новый экземпляр перечисления Status с указанным заголовком.
     *
     * @param title заголовок статуса
     */
    Status(String title) {
        this.title = title;
    }

    /**
     * Получает заголовок статуса.
     *
     * @return заголовок статуса
     */
    public String getTitle() {
        return title;
    }

    /**
     * Возвращает строковое представление статуса.
     *
     * @return строковое представление статуса
     */
    @Override
    public String toString() {
        return title;
    }
}
