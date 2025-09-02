package hexlet.code.dto;

import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;

import java.util.Map;

/**
 * DTO для коллекции URL с последними проверками.
 */
public class UrlsDto extends Base {
    private final Map<Url, UrlCheck> urlsWithLastChecks;

    /**
     * Конструктор.
     *
     * @param urlsWithLastChecks карта URL -> последняя проверка
     */
    public UrlsDto(Map<Url, UrlCheck> urlsWithLastChecks) {
        this.urlsWithLastChecks = urlsWithLastChecks;
    }

    /**
     * Возвращает карту URL и их последних проверок.
     *
     * @return карта URL -> UrlCheck
     */
    public Map<Url, UrlCheck> getUrlsWithLastChecks() {
        return urlsWithLastChecks;
    }
}
