package hexlet.code.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public final class Url {
    private Long id;
    private String name;
    private Timestamp createdAt;
    private List<UrlCheck> checks = new ArrayList<>();

    public Url(String name) {
        this.name = name;
    }
    public Url() {

    }
}
