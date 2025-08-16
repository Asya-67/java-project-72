package hexlet.code;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static Javalin getApp() {
        Javalin app = Javalin.create(config -> {
            config.pluggins.enableDevLogging();
        });
        app.get("/", ctx -> ctx.result("Hi"));
        return app;
    }

    public static void main(String[] args) {
        logger.info("Starting application...");
        Javalin app = getApp();

        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));
        app.start(port);
    }
}