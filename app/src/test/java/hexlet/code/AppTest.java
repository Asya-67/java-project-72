package hexlet.code;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class AppTest {
    private static DataSource dataSource;

    @BeforeAll
    static void setup() {
        dataSource = Database.getDataSource();
    }
    @BeforeEach
    void cleanDb() throws Exception {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE urls");
        }
    }

    @Test
    void testHomePage() {
        Javalin app = App.createApp(dataSource);
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/").asString();
            assertThat(response.getBody()).contains("Добавить сайт");
        });
    }

    @Test
    void testAddUrl() {
        Javalin app = App.createApp(dataSource);
        JavalinTest.test(app, (server, client) -> {
            client.post("/urls").form("url", "https://example.com").asString();

            UrlRepository repo = new UrlRepository(dataSource);
            List<Url> urls = repo.findAll();

            assertThat(urls).anyMatch(u -> u.getName().equals("https://example.com"));
        });
    }

    @Test
    void testUrlsPage() {
        UrlRepository repo = new UrlRepository(dataSource);
        repo.save(new Url("https://hexlet.io", new Timestamp(System.currentTimeMillis())));

        Javalin app = App.createApp(dataSource);
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls").asString();
            assertThat(response.getBody()).contains("https://hexlet.io");
        });
    }

    @Test
    void testShowUrlPage() {
        UrlRepository repo = new UrlRepository(dataSource);
        Url url = repo.save(new Url("https://hexlet.io", new Timestamp(System.currentTimeMillis())));

        Javalin app = App.createApp(dataSource);
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + url.getId()).asString();
            assertThat(response.getBody()).contains("https://hexlet.io");
        });
    }
}
