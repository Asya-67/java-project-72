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

                    if (databaseUrl.startsWith("postgres://")) {
                        databaseUrl = databaseUrl.replaceFirst("postgres://", "postgresql://");
                    }

                    URI dbUri = new URI(databaseUrl);

                    String userInfo = dbUri.getUserInfo();
                    if (userInfo == null || !userInfo.contains(":")) {
                        throw new RuntimeException("JDBC_DATABASE_URL не содержит username и password");
                    }

                    String[] userParts = userInfo.split(":");
                    username = userParts[0];
                    password = userParts[1];

                    jdbcUrl = "jdbc:postgresql://" + dbUri.getHost()
                            + ":" + dbUri.getPort()
                            + dbUri.getPath();

                    Class.forName("org.postgresql.Driver");
                } catch (URISyntaxException | ClassNotFoundException e) {
                    throw new RuntimeException("Неправильный JDBC_DATABASE_URL или драйвер PostgreSQL не найден", e);
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
