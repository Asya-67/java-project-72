package hexlet.code.dto;

/**
 * Base class for DTOs.
 * <p>
 * This class is designed for extension. Subclasses can inherit flash message and color properties.
 * When overriding methods, ensure that the behavior for getting/setting flash and color
 * remains consistent.
 */
public class Base {

    private String flash;
    private String color;

    public Base() {
    }

    /**
     * Returns the flash message.
     * <p>
     * Subclasses may override this method, but should ensure that
     * the flash message returned is consistent with the intended usage.
     *
     * @return the flash message
     */
    public String getFlash() {
        return flash;
    }

    /**
     * Sets the flash message.
     * <p>
     * Subclasses may override this method, but must ensure that
     * the flash message is properly handled to avoid inconsistencies.
     *
     * @param flash the flash message
     */
    public void setFlash(String flash) {
        this.flash = flash;
    }

    /**
     * Returns the color property.
     * <p>
     * Subclasses may override this method, but should maintain the contract
     * for color usage in the application.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color property.
     * <p>
     * Subclasses may override this method, but must ensure that
     * the color remains valid and consistent with the expected behavior.
     *
     * @param color the color
     */
    public void setColor(String color) {
        this.color = color;
    }
}
