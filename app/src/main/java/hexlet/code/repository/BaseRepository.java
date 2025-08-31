package hexlet.code.repository;

import javax.sql.DataSource;

public abstract class BaseRepository {

    protected static DataSource dataSource;

    public static void initDataSource(DataSource ds) {
        dataSource = ds;
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource не инициализирован. Вызовите BaseRepository.initDataSource()!");
        }
        return dataSource;
    }

    protected BaseRepository() {
    }
}
