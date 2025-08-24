package hexlet.code;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;

import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.sql.DataSource;


public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private static TemplateEngine createTemplateEngine() {
        ResourceCodeResolver resolver = new ResourceCodeResolver("templates");
        return TemplateEngine.create(resolver, ContentType.Html);
    }

    public static Javalin createApp(DataSource dataSource) {
        TemplateEngine templateEngine = createTemplateEngine();
        UrlRepository urlRepository = new UrlRepository(dataSource);

        Javalin app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(templateEngine));
        });

        app.get("/", ctx -> {
            String flash = ctx.sessionAttribute("flash");
            ctx.sessionAttribute("flash", null);

            Map<String, Object> model = new HashMap<>();
            model.put("flash", flash);
            ctx.render("index.jte", model);
        });

        app.post("/urls", ctx -> {
            String inputUrl = ctx.formParam("url");
            if (inputUrl == null || inputUrl.isBlank()) {
                ctx.sessionAttribute("flash", "Некорректный URL");
                ctx.redirect("/");
                return;
            }

            try {
                URL url = URI.create(inputUrl).toURL();
                String domain = url.getProtocol() + "://" + url.getHost();
                if (url.getPort() != -1) {
                    domain += ":" + url.getPort();
                }

                Url existing = urlRepository.findByName(domain);

                if (existing != null) {
                    ctx.sessionAttribute("flash", "Страница уже существует");
                } else {
                    urlRepository.save(new Url(domain, new Timestamp(System.currentTimeMillis())));
                    ctx.sessionAttribute("flash", "Страница успешно добавлена");
                }
            } catch (Exception e) {
                ctx.sessionAttribute("flash", "Некорректный URL");
            }

            ctx.redirect("/");
        });

        app.get("/urls", ctx -> {
            List<Url> urls = urlRepository.findAll();
            ctx.attribute("urls", urls);
            ctx.render("urls.jte");
        });

        app.get("/urls/{id}", ctx -> {
            long id = Long.parseLong(ctx.pathParam("id"));
            Url url = urlRepository.findById(id);
            ctx.attribute("url", url);
            ctx.render("showUrl.jte");
        });

        return app;
    }

    public static void main(String[] args) {
        LOGGER.info("Starting application...");
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));
        Javalin app = createApp(Database.getDataSource());
        app.start(port);
        LOGGER.info("Application started on port {}", port);
    }
}
