package hexlet.code;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import hexlet.code.utils.TestUtils;
import javax.sql.DataSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppIntegrationTest {
    private DataSource dataSource;
    private static MockWebServer mockServer;
    private Javalin app;
    private Map<String, Object> existingUrl;
    private Map<String, Object> existingUrlCheck;

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    private static String readFixture(String fileName) throws IOException {
        return Files.readString(getFixturePath(fileName)).trim();
    }

    @BeforeAll
    void beforeAll() throws IOException {
        mockServer = new MockWebServer();
        MockResponse mockedResponse = new MockResponse().setBody(readFixture("index.html"));
        mockServer.enqueue(mockedResponse);
        mockServer.start();
    }

    @AfterAll
    void afterAll() throws IOException {
        mockServer.shutdown();
    }

    @BeforeEach
    void setUp() throws IOException, SQLException {
        app = App.getApp();
        dataSource = Database.getDataSource();

        var schema = AppIntegrationTest.class.getClassLoader().getResource("schema.sql");
        var file = new File(schema.getFile());
        var sql = Files.lines(file.toPath()).collect(Collectors.joining("\n"));

        try (var conn = dataSource.getConnection(); var stmt = conn.createStatement()) {
            stmt.execute(sql);
        }

        String url = "https://en.hexlet.io";
        TestUtils.addUrl(dataSource, url);
        existingUrl = TestUtils.getUrlByName(dataSource, url);
        TestUtils.addUrlCheck(dataSource, (long) existingUrl.get("id"));
        existingUrlCheck = TestUtils.getUrlCheck(dataSource, (long) existingUrl.get("id"));
    }

    @Nested
    class RootTest {
        @Test
        void testIndex() {
            JavalinTest.test(app, (server, client) -> {
                try (var response = client.get("/")) {
                    assertThat(response.code()).isEqualTo(200);

                    String body = response.body().string();
                    assertThat(body).contains("Добавьте URL");
                }
            });
        }

    @Nested
    class UrlTest {
        @Test
        void testUrlsList() {
            JavalinTest.test(app, (server, client) -> {
                try (var response = client.get("/urls")) {
                    assertThat(response.code()).isEqualTo(200);
                    String body = response.body().string();
                    assertThat(body).contains(existingUrl.get("name").toString());

                    if (existingUrlCheck.get("created_at") != null) {
                        String formattedDate = existingUrlCheck.get("created_at").toString();
                        assertThat(body).contains(formattedDate);
                    }
                }
            });
        }

        @Test
        void testShowUrl() {
            JavalinTest.test(app, (server, client) -> {
                try (var response = client.get("/urls/" + existingUrl.get("id"))) {
                    assertThat(response.code()).isEqualTo(200);
                    String body = response.body().string();
                    assertThat(body).contains(existingUrl.get("name").toString())
                            .contains(existingUrlCheck.get("status_code").toString());
                }
            });
        }

        @Test
        void testStoreUrl() {
            String inputUrl = "https://ru.hexlet.io";
            JavalinTest.test(app, (server, client) -> {
                var requestBody = "url=" + inputUrl;
                try (var postResponse = client.post("/urls", requestBody)) {
                    assertThat(postResponse.code()).isEqualTo(200);
                }

                try (var getResponse = client.get("/urls")) {
                    assertThat(getResponse.code()).isEqualTo(200);
                    assertThat(getResponse.body().string()).contains(inputUrl);
                }

                var actualUrl = TestUtils.getUrlByName(Database.getDataSource(), inputUrl);
                assertThat(actualUrl).isNotNull();
                assertThat(actualUrl.get("name").toString()).isEqualTo(inputUrl);
            });
        }
    }

    @Nested
    class UrlCheckTest {
        @Test
        void testStoreUrlCheck() {
            String url = mockServer.url("/").toString().replaceAll("/$", "");
            JavalinTest.test(app, (server, client) -> {
                var requestBody = "url=" + url;
                try (var postResponse = client.post("/urls", requestBody)) {
                    assertThat(postResponse.code()).isEqualTo(200);
                }

                var actualUrl = TestUtils.getUrlByName(Database.getDataSource(), url);
                assertThat(actualUrl).isNotNull();
                assertThat(actualUrl.get("name").toString()).isEqualTo(url);

                try (var checkResponse = client.post("/urls/" + actualUrl.get("id") + "/checks")) {
                    assertThat(checkResponse.code()).isEqualTo(200);
                }

                try (var getResponse = client.get("/urls/" + actualUrl.get("id"))) {
                    assertThat(getResponse.code()).isEqualTo(200);
                }

                var actualCheck = TestUtils.getUrlCheck(Database.getDataSource(), (long) actualUrl.get("id"));
                assertThat(actualCheck).isNotNull();
                assertThat(actualCheck.get("title")).isEqualTo("Test page");
                assertThat(actualCheck.get("h1")).isEqualTo("Do not expect a miracle, miracles yourself!");
                assertThat(actualCheck.get("description")).isEqualTo("statements of great people");
            });
        }
    }
}
