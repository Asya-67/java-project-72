package hexlet.code.dto;

import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UrlDto extends Base {
    private Url url;
    private List<UrlCheck> urlChecks;
}
