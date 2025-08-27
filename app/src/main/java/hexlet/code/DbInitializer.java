package hexlet.code;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbInitializer {

    private static void executeSqlFile(Connection conn, String resourceName) throws IOException, SQLException {
        try (InputStream is = DbInitializer.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new IOException("Resource not found: " + resourceName);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                StringBuilder sql = new StringBuilder();
                try (Statement stmt = conn.createStatement()) {
                    while ((line = reader.readLine()) != null) {
                        line = line.strip();
                        if (line.isEmpty() || line.startsWith("--")) {
                            continue;
                        }
                        sql.append(line);
                        if (line.endsWith(";")) {
                            stmt.execute(sql.toString());
                            sql.setLength(0);
                        } else {
                            sql.append(" ");
                        }
                    }
                }
            }
        }
    }

    public static void init(DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            executeSqlFile(conn, "schema.sql");
            executeSqlFile(conn, "data.sql");
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}
