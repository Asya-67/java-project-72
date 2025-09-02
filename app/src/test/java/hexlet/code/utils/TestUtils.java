package hexlet.code.utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TestUtils {

    public static void addUrl(DataSource dataSource, String name) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO urls(name) VALUES (?)")) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }

    public static Map<String, Object> getUrlByName(DataSource dataSource, String name) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM urls WHERE name = ?")) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", rs.getLong("id"));
                    map.put("name", rs.getString("name"));
                    return map;
                } else {
                    return null;
                }
            }
        }
    }

    public static void addUrlCheck(DataSource dataSource, long urlId) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO url_checks(url_id, status_code, title, h1, description) VALUES (?, 200, 'Test page',"
                             + "'Do not expect a miracle, miracles yourself!', 'statements of great people')")) {
            stmt.setLong(1, urlId);
            stmt.executeUpdate();
        }
    }

    public static Map<String, Object> getUrlCheck(DataSource dataSource, long urlId) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM url_checks WHERE url_id = ?")) {
            stmt.setLong(1, urlId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("status_code", rs.getInt("status_code"));
                    map.put("title", rs.getString("title"));
                    map.put("h1", rs.getString("h1"));
                    map.put("description", rs.getString("description"));
                    return map;
                } else {
                    return null;
                }
            }
        }
    }
}
