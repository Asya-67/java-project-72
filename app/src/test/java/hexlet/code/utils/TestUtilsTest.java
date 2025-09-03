package hexlet.code.utils;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import hexlet.code.Database;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtilsTest {

    private static final DataSource DATA_SOURCE = Database.getDataSource();

    @Test
    void testAddAndGetUrl() throws SQLException {
        String name = "https://example.com";
        TestUtils.addUrl(DATA_SOURCE, name);

        Map<String, Object> result = TestUtils.getUrlByName(DATA_SOURCE, name);
        assertThat(result).isNotNull();
        assertThat(result.get("name")).isEqualTo(name);
    }

    @Test
    void testAddAndGetUrlCheck() throws SQLException {
        String name = "https://example.com/check";
        TestUtils.addUrl(DATA_SOURCE, name);
        Map<String, Object> url = TestUtils.getUrlByName(DATA_SOURCE, name);
        assertThat(url).isNotNull();

        long urlId = ((Number) url.get("id")).longValue();
        TestUtils.addUrlCheck(DATA_SOURCE, urlId);

        Map<String, Object> check = TestUtils.getUrlCheck(DATA_SOURCE, urlId);
        assertThat(check).isNotNull();
        assertThat(check.get("status_code")).isEqualTo(200);
    }
}
