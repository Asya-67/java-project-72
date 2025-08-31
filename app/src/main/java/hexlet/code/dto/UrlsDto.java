package hexlet.code.dto;

import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class UrlsDto extends Base {
    private Map<Url, UrlCheck> urlsWithLastChecks;
}
