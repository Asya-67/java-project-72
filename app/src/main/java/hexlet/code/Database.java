package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class Database {

    private static HikariDataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();

            String databaseUrl = System.getenv("JDBC_DATABASE_URL");

            if (databaseUrl != null && !databaseUrl.isBlank()) {

                if (!databaseUrl.startsWith("jdbc:")) {
                    databaseUrl = "jdbc:" + databaseUrl;
                }
                config.setJdbcUrl(databaseUrl);
                config.setDriverClassName("org.postgresql.Driver");
            } else {

                config.setJdbcUrl("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
                config.setUsername("sa");
                config.setPassword("");
            }

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
