package hexlet.code;

import io.javalin.Javalin;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppTest {

    private Javalin app;
    private final String url = "http://localhost:7000";

    @BeforeAll
    void startServer() throws InterruptedException {
        System.setProperty("ENV", "test");
        App.getApp();
        app = App.getApp();
        app.start(7000);
        Thread.sleep(500);
    }

    @AfterAll
    void stopServer() {
        if (app != null) {
            app.stop();
        }
    }

    @BeforeEach
    void cleanDb() throws Exception {
        try (Connection conn = App.getDataSource().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM urls");
        }
    }

    @Test
    void testAddValidUrlShowsFlash() {
        String testUrl = "https://example.org";

        HttpResponse<String> response = Unirest.post(url + "/urls")
                .field("url", testUrl)
                .asString();

        assertTrue(response.getBody().contains("Страница успешно добавлена"));
        assertTrue(urlExistsInDb(testUrl));
    }

    @Test
    void testAddDuplicateUrlShowsFlash() {
        String testUrl = "https://example.org";

        Unirest.post(url + "/urls").field("url", testUrl).asString();

        HttpResponse<String> response = Unirest.post(url + "/urls").field("url", testUrl).asString();

        assertTrue(response.getBody().contains("Страница уже существует"));
    }

    @Test
    void testAddInvalidUrlShowsFlash() {
        String invalidUrl = "invalid-url";

        HttpResponse<String> response = Unirest.post(url + "/urls")
                .field("url", invalidUrl)
                .asString();

        assertTrue(response.getBody().contains("Некорректный URL"));
    }

    private boolean urlExistsInDb(String testUrl) {
        try (Connection conn = App.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT COUNT(*) AS cnt FROM urls WHERE name='" + testUrl + "'")) {
            rs.next();
            return rs.getInt("cnt") > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
