package hexlet.code.repository;

import javax.sql.DataSource;

public abstract class BaseRepository {
    protected static DataSource dataSource;

    public static void initDataSource(DataSource ds) {
        dataSource = ds;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    protected BaseRepository() {

    }
}
