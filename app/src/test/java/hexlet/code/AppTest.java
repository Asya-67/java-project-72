package hexlet.code;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import io.javalin.testtools.HttpResponse;
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
            HttpResponse<String> response = client.get("/");
            assertThat(response.body()).contains("Добавить сайт");
        });
    }

    @Test
    void testAddUrl() throws Exception {
        Javalin app = App.createApp(dataSource);
        JavalinTest.test(app, (server, client) -> {
            HttpResponse<String> response = client.post("/urls", "url=https://example.com");

            assertThat(response.code()).isEqualTo(200);

            UrlRepository repo = new UrlRepository(dataSource);
            List<Url> urls = repo.findAll();

            assertThat(urls).anyMatch(u -> u.getName().equals("https://example.com"));
        });
    }

    @Test
    void testUrlsPage() throws Exception {
        UrlRepository repo = new UrlRepository(dataSource);
        repo.save(new Url("https://hexlet.io", new Timestamp(System.currentTimeMillis())));

        Javalin app = App.createApp(dataSource);
        JavalinTest.test(app, (server, client) -> {
            HttpResponse<String> response = client.get("/urls");
            assertThat(response.body()).contains("https://hexlet.io");
        });
    }

    @Test
    void testShowUrlPage() throws Exception {
        UrlRepository repo = new UrlRepository(dataSource);
        Url url = new Url("https://hexlet.io", new Timestamp(System.currentTimeMillis()));
        repo.save(url);

        Javalin app = App.createApp(dataSource);
        JavalinTest.test(app, (server, client) -> {
            HttpResponse<String> response = client.get("/urls/" + url.getId());
            assertThat(response.body()).contains("https://hexlet.io");
        });
    }
}
