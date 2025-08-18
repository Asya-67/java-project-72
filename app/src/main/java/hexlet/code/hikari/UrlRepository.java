package hexlet.code.repository;

import hexlet.code.model.Url;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UrlRepository extends BaseRepository {

    public UrlRepository(DataSource dataSource) {
        super(dataSource);
    }

    public void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, url.getCreatedAt());
            stmt.executeUpdate();
        }
    }

    public List<Url> findAll() throws SQLException {
        String sql = "SELECT id, name, created_at FROM urls";
        List<Url> urls = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                urls.add(new Url(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getTimestamp("created_at")
                ));
            }
        }
        return urls;
    }
}
