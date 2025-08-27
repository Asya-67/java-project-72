package hexlet.code;

import hexlet.code.controllers.UrlsController;
import hexlet.code.controllers.UrlChecksController;
import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import javax.sql.DataSource;
import java.sql.SQLException;

public class App {

    private static DataSource dataSource;

    /**
     * Создаёт экземпляр Javalin приложения.
     *
     * @return объект Javalin приложения.
     * @throws SQLException если не удалось подключиться к базе данных.
     */
    public static Javalin getApp() throws SQLException {
        dataSource = Database.getDataSource();
        DbInitializer.init(dataSource);
        BaseRepository.initDataSource(dataSource);

        var app = Javalin.create(config -> config.plugins.enableDevLogging());
        JavalinJte.init(Methods.createTemplateEngine());

        app.get(AppPaths.root(), UrlsController::showMainPage);
        app.get(AppPaths.urls(), UrlsController::showUrlList);
        app.post(AppPaths.urls(), UrlsController::createUrl);

        app.get(AppPaths.url("{id}"), UrlsController::showUrl);
        app.post(AppPaths.urlChecks("{id}"), UrlChecksController::makeCheck);

        return app;
    }

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки.
     * @throws SQLException если не удалось подключиться к базе данных.
     */
    public static void main(String[] args) throws SQLException {
        var app = getApp();
        app.start(7000);
    }

    /**
     * Запускает приложение Javalin на указанном порту (для тестов).
     *
     * @param port порт, на котором будет запущен сервер.
     * @return объект Javalin приложения.
     * @throws SQLException если не удалось подключиться к базе данных.
     */
    public static Javalin startAppForTests(int port) throws SQLException {
        Javalin app = getApp();
        app.start(port);
        return app;
    }

    /**
     * Получение DataSource приложения.
     *
     * @return объект DataSource.
     */
    public static DataSource getDataSource() {
        return dataSource;
    }
}
