package hexlet.code.dto;

import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;

import java.util.List;

/**
 * DTO для URL и связанных проверок.
 */
public class UrlDto extends Base {
    private Url url;
    private List<UrlCheck> checks;

    /**
     * Создает объект DTO для URL.
     *
     * @param url объект URL
     * @param checks список проверок URL
     */
    public UrlDto(Url url, List<UrlCheck> checks) {
        this.url = url;
        this.checks = checks;
    }

    /**
     * Возвращает URL.
     *
     * @return объект URL
     */
    public Url getUrl() {
        return url;
    }

    /**
     * Возвращает список проверок URL.
     *
     * @return список UrlCheck
     */
    public List<UrlCheck> getChecks() {
        return checks;
    }
}
