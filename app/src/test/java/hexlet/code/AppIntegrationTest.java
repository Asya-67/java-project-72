package hexlet.code;

import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;
import hexlet.code.repository.UrlRepository;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.BaseRepository;

import static org.awaitility.Awaitility.await;
import java.util.concurrent.TimeUnit;

import io.javalin.Javalin;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppIntegrationTest {

    private Javalin app;
    private String baseUrl;
    private MockWebServer mockWebServer;

    @BeforeAll
    void beforeAll() throws SQLException, IOException {
        System.setProperty("ENV", "test");

        mockWebServer = new MockWebServer();
        mockWebServer.start();

        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        ds.setUser("sa");
        ds.setPassword("");

        DbInitializer.init(ds);
        BaseRepository.initDataSource(ds);

        app = App.getApp();
        app.start(0);

        baseUrl = "http://localhost:" + app.port();
    }

    @AfterAll
    void afterAll() throws Exception {
        app.stop();
        if (mockWebServer != null) {
            mockWebServer.shutdown();
        }
    }

    @BeforeEach
    void beforeEach() throws SQLException {
        try (var conn = BaseRepository.getDataSource().getConnection();
             var stmt = conn.createStatement()) {

            stmt.executeUpdate("SET REFERENTIAL_INTEGRITY FALSE");

            stmt.executeUpdate("TRUNCATE TABLE url_checks");
            stmt.executeUpdate("TRUNCATE TABLE urls");

            stmt.executeUpdate("SET REFERENTIAL_INTEGRITY TRUE");
        }
    }

    @Test
    void testMainPage() {
        HttpResponse<String> response = Unirest.get(baseUrl + "/").asString();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getBody()).contains("Добавьте URL");
    }

    @Test
    void testCreateValidUrl() throws SQLException {
        String url = "https://example.com";
        HttpResponse<String> response = Unirest.post(baseUrl + "/urls")
                .field("url", url)
                .asString();

        assertThat(response.getStatus()).isEqualTo(302);

        List<Url> urls = UrlRepository.findAll();
        assertThat(urls).extracting(Url::getName).contains(url);

        assertThat(response.getHeaders().getFirst("Location")).isEqualTo("/urls/" + urls.get(0).getId());
    }

    @Test
    void testCreateInvalidUrl() {
        HttpResponse<String> response = Unirest.post(baseUrl + "/urls")
                .field("url", "invalid-url")
                .asString();

        assertThat(response.getStatus()).isEqualTo(302);

        assertThat(response.getHeaders().getFirst("Location")).isEqualTo("/");

        HttpResponse<String> mainPage = Unirest.get(baseUrl + "/").asString();
        assertThat(mainPage.getBody()).contains("Некорректный URL");
    }

    @Test
    void testUrlsListAndShowUrl() throws SQLException {
        Url url = UrlRepository.save(new Url("https://example.org"));

        List<Url> urls = UrlRepository.findAll();
        assertThat(urls).isNotEmpty();

        Long id = urls.get(0).getId();
        HttpResponse<String> response = Unirest.get(baseUrl + "/urls/" + id).asString();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getBody()).contains(urls.get(0).getName());
    }

    @Test
    void testUrlCheckWithSeo() throws Exception {

        String html = "<html><head><title>My Title</title>"
                + "<meta name='description' content='My Description'></head>"
                + "<body><h1>My H1</h1></body></html>";
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(html));

        String testUrl = mockWebServer.url("/").toString();
        Url savedUrl = UrlRepository.save(new Url(testUrl));

        HttpResponse<String> response = Unirest.post(baseUrl + "/urls/" + savedUrl.getId() + "/checks")
                .asString();

        assertThat(response.getStatus()).isEqualTo(302);

        await().atMost(2, TimeUnit.SECONDS).until(() ->
                !UrlCheckRepository.findByUrlId(savedUrl.getId()).isEmpty()
        );

        List<UrlCheck> checks = UrlCheckRepository.findByUrlId(savedUrl.getId());
        assertThat(checks).isNotEmpty();

        UrlCheck check = checks.get(0);
        assertThat(check.getTitle()).isEqualTo("My Title");
        assertThat(check.getH1()).isEqualTo("My H1");
        assertThat(check.getDescription()).isEqualTo("My Description");
        assertThat(check.getStatusCode()).isEqualTo(200);
        assertThat(check.getCreatedAt()).isNotNull();
    }
}
