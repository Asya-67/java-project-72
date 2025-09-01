package hexlet.code;

import hexlet.code.controllers.UrlChecksController;
import hexlet.code.controllers.UrlsController;
import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import javax.sql.DataSource;
import java.sql.SQLException;

public class App {

    private static DataSource dataSource;

    public static Javalin getApp() throws SQLException {
        // Инициализация базы данных
        dataSource = Database.getDataSource();
        DbInitializer.init(dataSource);
        BaseRepository.initDataSource(dataSource);

        // Создание Javalin
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.bundledPlugins.enableRouteOverview("/routes");
            config.fileRenderer(new JavalinJte(Methods.createTemplateEngine()));
        });

        // Роуты
        app.get(AppPaths.mainPath(), UrlsController::showMainPage);
        app.get(AppPaths.urlsPath(), UrlsController::showUrlList);
        app.post(AppPaths.urlsPath(), UrlsController::createUrl);

        app.get(AppPaths.url("{id}"), UrlsController::showUrl);
        app.post(AppPaths.urlChecks("{id}"), UrlChecksController::makeCheck);

        return app;
    }

    public static void main(String[] args) throws SQLException {
        Javalin app = getApp();
        app.start(Methods.getPort()); // Используем метод для порта
    }

    public static Javalin startAppForTests(int port) throws SQLException {
        Javalin app = getApp();
        app.start(port);
        return app;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
