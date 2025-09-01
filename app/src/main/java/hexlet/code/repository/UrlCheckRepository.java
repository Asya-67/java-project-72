package hexlet.code.repository;

import hexlet.code.models.UrlCheck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public final class UrlCheckRepository extends BaseRepository {

    private UrlCheckRepository() {

    }

    public static UrlCheck save(UrlCheck check) throws SQLException {
        String sql = "INSERT INTO url_checks (status_code, title, h1, description, url_id, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, check.getStatusCode());
            ps.setString(2, check.getTitle());
            ps.setString(3, check.getH1());
            ps.setString(4, check.getDescription());
            ps.setLong(5, check.getUrlId());

            Timestamp timestamp = check.getCreatedAt() != null
                    ? check.getCreatedAt()
                    : new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(6, timestamp);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    check.setId(rs.getLong(1));
                }
            }
        }
        return check;
    }

    public static List<UrlCheck> findByUrlId(Long urlId) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY created_at DESC";
        List<UrlCheck> checks = new ArrayList<>();
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, urlId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UrlCheck check = new UrlCheck();
                    check.setId(rs.getLong("id"));
                    check.setUrlId(rs.getLong("url_id"));
                    check.setStatusCode(rs.getInt("status_code"));
                    check.setTitle(rs.getString("title"));
                    check.setH1(rs.getString("h1"));
                    check.setDescription(rs.getString("description"));
                    check.setCreatedAt(rs.getTimestamp("created_at"));
                    checks.add(check);
                }
            }
        }
        return checks;
    }

    public static Optional<UrlCheck> findLastCheckByUrlId(Long urlId) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC LIMIT 1";
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, urlId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UrlCheck check = new UrlCheck();
                    check.setId(rs.getLong("id"));
                    check.setUrlId(rs.getLong("url_id"));
                    check.setStatusCode(rs.getInt("status_code"));
                    check.setTitle(rs.getString("title"));
                    check.setH1(rs.getString("h1"));
                    check.setDescription(rs.getString("description"));
                    check.setCreatedAt(rs.getTimestamp("created_at"));
                    return Optional.of(check);
                }
            }
        }
        return Optional.empty();
    }
}
