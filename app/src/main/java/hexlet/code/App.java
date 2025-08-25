package hexlet.code;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.models.Url;
import hexlet.code.repository.UrlRepository;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

public class App {
    private static DataSource dataSource;
    private static UrlRepository urlRepository;

    public static void main(String[] args) {
        initApp();
        Javalin app = getApp();

        String port = System.getenv("PORT");
        int portNumber = port != null ? Integer.parseInt(port) : 7000;

        app.start(portNumber);
    }

    public static void initApp() {
        if (dataSource == null) {
            dataSource = Database.getDataSource();
            DbInitializer.init(dataSource);
            urlRepository = new UrlRepository(dataSource);
        }
    }

    public static Javalin getApp() {
        initApp();

        return Javalin.create(config -> config.fileRenderer(new JavalinJte(createTemplateEngine())))
                .get("/", ctx -> ctx.render("index.jte"))
                .post("/urls", ctx -> {
                    String inputUrl = ctx.formParam("url");
                    if (inputUrl == null || inputUrl.isEmpty()) {
                        handleFlash(ctx, "Некорректный URL");
                        return;
                    }

                    String baseUrl;
                    try {
                        URI uri = new URI(inputUrl);
                        if (uri.getHost() == null || uri.getScheme() == null) {
                            handleFlash(ctx, "Некорректный URL");
                            return;
                        }
                        baseUrl = uri.getScheme() + "://" + uri.getHost();
                        if (uri.getPort() != -1) {
                            baseUrl += ":" + uri.getPort();
                        }
                    } catch (URISyntaxException e) {
                        handleFlash(ctx, "Некорректный URL");
                        return;
                    }

                    if (urlRepository.exists(baseUrl)) {
                        handleFlash(ctx, "Страница уже существует");
                    } else {
                        Url url = new Url(null, baseUrl, LocalDateTime.now());
                        urlRepository.add(url);
                        handleFlash(ctx, "Страница успешно добавлена");
                    }
                })
                .get("/urls", ctx -> {
                    List<Url> urls = urlRepository.findAll();
                    ctx.attribute("urls", urls);
                    String flash = getFlash(ctx);
                    ctx.attribute("flash", flash);
                    ctx.render("urls.jte");
                });
    }

    private static void handleFlash(io.javalin.http.Context ctx, String message) {
        String env = System.getProperty("ENV", "");
        if ("test".equals(env)) {
            ctx.result(message);
        } else {
            setFlash(ctx, message);
            ctx.redirect("/urls");
        }
    }

    private static TemplateEngine createTemplateEngine() {
        ResourceCodeResolver resolver = new ResourceCodeResolver("templates", App.class.getClassLoader());
        return TemplateEngine.create(resolver, ContentType.Html);
    }

    private static void setFlash(io.javalin.http.Context ctx, String message) {
        ctx.sessionAttribute("flash", message);
    }

    private static String getFlash(io.javalin.http.Context ctx) {
        String message = ctx.sessionAttribute("flash");
        ctx.sessionAttribute("flash", null);
        return message;
    }

    public static DataSource getDataSource() {
        initApp();
        return dataSource;
    }
}
