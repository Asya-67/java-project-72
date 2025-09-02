package hexlet.code.dto;

/**
 * Базовый класс для DTO с flash-сообщениями и цветом.
 */
public final class Base {
    private String flash;
    private String color;

    /**
     * Создает новый объект Base.
     */
    public Base() {
    }

    /**
     * Возвращает flash-сообщение.
     *
     * @return сообщение
     */
    public String getFlash() {
        return flash;
    }

    /**
     * Устанавливает flash-сообщение.
     *
     * @param flash сообщение
     */
    public void setFlash(String flash) {
        this.flash = flash;
    }

    /**
     * Возвращает цвет.
     *
     * @return цвет
     */
    public String getColor() {
        return color;
    }

    /**
     * Устанавливает цвет.
     *
     * @param color цвет
     */
    public void setColor(String color) {
        this.color = color;
    }
}
