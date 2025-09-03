package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

public class Database {

    private static HikariDataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();

            String jdbcUrl;
            String username;
            String password;

            String databaseUrl = System.getenv("JDBC_DATABASE_URL");

            if (databaseUrl != null && !databaseUrl.isBlank()) {
                try {
                    URI dbUri = new URI(databaseUrl);
                    String[] userInfo = dbUri.getUserInfo().split(":");
                    username = userInfo[0];
                    password = userInfo[1];
                    jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();

                    Class.forName("org.postgresql.Driver");
                } catch (URISyntaxException | NullPointerException | ClassNotFoundException e) {
                    throw new RuntimeException("Invalid JDBC_DATABASE_URL or PostgreSQL driver not found", e);
                }
            } else {

                jdbcUrl = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;MODE=PostgreSQL";
                username = "sa";
                password = "";
                try {
                    Class.forName("org.h2.Driver");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("H2 Driver not found", e);
                }
            }

            config.setJdbcUrl(jdbcUrl);
            config.setUsername(username);
            config.setPassword(password);
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setMaxLifetime(1800000);
            config.setConnectionTimeout(30000);

            dataSource = new HikariDataSource(config);
        }

        return dataSource;
    }

    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
