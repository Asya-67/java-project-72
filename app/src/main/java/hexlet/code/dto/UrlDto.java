package hexlet.code.dto;

import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;

import java.util.List;

public class UrlDto extends Base {
    private Url url;
    private List<UrlCheck> checks;

    public UrlDto(Url url, List<UrlCheck> checks) {
        this.url = url;
        this.checks = checks;
    }

    public Url getUrl() {
        return url;
    }

    public List<UrlCheck> getChecks() {
        return checks;
    }
}
