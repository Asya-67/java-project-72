package hexlet.code;

import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;
import hexlet.code.repository.UrlRepository;
import hexlet.code.repository.UrlCheckRepository;
import io.javalin.Javalin;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.TestInstance;
import org.h2.jdbcx.JdbcDataSource;
import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import hexlet.code.repository.BaseRepository;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppIntegrationTest {

    private Javalin app;
    private String baseUrl;
    private MockWebServer mockWebServer;

    @BeforeAll
    void beforeAll() {
        System.setProperty("ENV", "test");

        try {
            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
            ds.setUser("sa");
            ds.setPassword("");

            DbInitializer.init(ds);
            BaseRepository.initDataSource(ds);

            app = App.getApp();
            app.start(0);
            baseUrl = "http://localhost:" + app.port();

            mockWebServer = new MockWebServer();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    void afterAll() throws Exception {
        app.stop();
        if (mockWebServer != null) {
            mockWebServer.shutdown();
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

        List<Url> urls = new UrlRepository().findAll();
        assertThat(urls).extracting(Url::getName).contains(url);
    }

    @Test
    void testCreateInvalidUrl() {
        HttpResponse<String> response = Unirest.post(baseUrl + "/urls")
                .field("url", "invalid-url")
                .asString();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getBody()).contains("Некорректный URL");
    }

    @Test
    void testUrlsListAndShowUrl() throws SQLException {
        UrlRepository urlsRepo = new UrlRepository();
        List<Url> urls = urlsRepo.findAll();
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
        mockWebServer.start();
        String testUrl = mockWebServer.url("/").toString();

        UrlRepository urlRepo = new UrlRepository();
        Url savedUrl = urlRepo.save(new Url(testUrl));

        HttpResponse<String> response = Unirest.post(baseUrl + "/urls/" + savedUrl.getId()
                + "/checks").asString();
        assertThat(response.getStatus()).isEqualTo(302);

        UrlCheckRepository checkRepo = new UrlCheckRepository();
        List<UrlCheck> checks = checkRepo.findByUrlId(savedUrl.getId());
        assertThat(checks).isNotEmpty();

        UrlCheck check = checks.get(0);
        assertThat(check.getTitle()).isEqualTo("My Title");
        assertThat(check.getH1()).isEqualTo("My H1");
        assertThat(check.getDescription()).isEqualTo("My Description");
    }
}
