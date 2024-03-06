package com.example.pmbakanov.models.enums;

/**
 * Перечисление ExecutorName представляет возможные имена исполнителей задач.
 * Каждое имя исполнителя связано с определенным сотрудником и представлено в виде строки.
 */
public enum ExecutorName {
    NEW("Не назначен"), ORLOV("Орлов А."), ZHUKOV("Жуков Д."), GLUSHKOV("Глушков А.");
    private final String title;

    /**
     * Создает новый экземпляр перечисления ExecutorName с указанным заголовком.
     *
     * @param title заголовок исполнителя
     */
    ExecutorName(String title) {
        this.title = title;
    }

    /**
     * Получает заголовок исполнителя.
     *
     * @return заголовок исполнителя
     */
    public String getTitle() {
        return title;
    }

    /**
     * Возвращает строковое представление имени исполнителя.
     *
     * @return строковое представление имени исполнителя
     */
    @Override
    public String toString() {
        return title;
    }
}
