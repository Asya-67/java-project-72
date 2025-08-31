package hexlet.code.dto;

import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;

import java.util.Map;

public class UrlsDto extends Base {
    private final Map<Url, UrlCheck> urlsWithLastChecks;

    public UrlsDto(Map<Url, UrlCheck> urlsWithLastChecks) {
        this.urlsWithLastChecks = urlsWithLastChecks;
    }

    public Map<Url, UrlCheck> getUrlsWithLastChecks() {
        return urlsWithLastChecks;
    }
}
