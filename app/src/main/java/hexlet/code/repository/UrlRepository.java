package hexlet.code.repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import hexlet.code.models.Url;
import java.sql.Timestamp;

public class UrlRepository extends BaseRepository {

    public UrlRepository(DataSource dataSource) {
        super(dataSource);
    }

    public void init() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS urls ("
                    + "id IDENTITY PRIMARY KEY, "
                    + "name VARCHAR(255) UNIQUE, "
                    + "created_at TIMESTAMP"
                    + ")");
        }
    }

    public void add(Url url) throws SQLException {
        String sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, url.getName());
            ps.setTimestamp(2, Timestamp.valueOf(url.getCreatedAt()));
            ps.executeUpdate();
        }
    }

    public boolean exists(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM urls WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public List<Url> findAll() throws SQLException {
        List<Url> urls = new ArrayList<>();
        String sql = "SELECT id, name, created_at FROM urls";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                urls.add(new Url(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        }
        return urls;
    }
}
