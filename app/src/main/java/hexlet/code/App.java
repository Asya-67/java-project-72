package hexlet.code;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.info("Starting application...");


        TemplateEngine templateEngine = createTemplateEngine();


        Javalin app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(templateEngine));
        });


        app.get("/", ctx -> ctx.render("index.jte"));


        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));
        app.start(port);
        LOGGER.info("Application started on port {}", port);
    }


    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }
}
