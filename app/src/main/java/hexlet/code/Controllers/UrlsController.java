package hexlet.code.controllers;

import hexlet.code.Methods;
import hexlet.code.models.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UrlsController {

    private static final UrlRepository URL_REPOSITORY = new UrlRepository();

    public static void showMainPage(Context ctx) {
        String flash = Methods.getFlash(ctx);
        ctx.render("index.jte", Map.of("flash", flash == null ? "" : flash));
    }

    public static void showUrlList(Context ctx) throws SQLException {
        List<Url> urls = URL_REPOSITORY.findAll();
        String flash = Methods.getFlash(ctx);
        ctx.render("urls.jte", Map.of(
                "urls", urls,
                "flash", flash == null ? "" : flash
        ));
    }

    public static void showUrl(Context ctx) throws SQLException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        var urlOpt = URL_REPOSITORY.findById(id);
        if (urlOpt.isEmpty()) {
            ctx.status(404);
            return;
        }
        Url url = urlOpt.get();
        String flash = Methods.getFlash(ctx);
        ctx.render("url.jte", Map.of(
                "url", url,
                "flash", flash == null ? "" : flash
        ));
    }

    public static void createUrl(Context ctx) {
        String inputUrl = ctx.formParam("url");

        if (inputUrl == null || inputUrl.isBlank()) {
            Methods.handleFlash(ctx, "Некорректный URL");
            return;
        }

        String baseUrl;
        try {
            URI uri = new URI(inputUrl);
            var url = uri.toURL(); // полная валидация
            baseUrl = url.getProtocol() + "://" + url.getHost();
            if (url.getPort() != -1) {
                baseUrl += ":" + url.getPort();
            }
        } catch (Exception e) {
            Methods.handleFlash(ctx, "Некорректный URL");
            return;
        }

        try {
            if (URL_REPOSITORY.exists(baseUrl)) {
                Methods.handleFlash(ctx, "Страница уже существует");
                return;
            }
            Url url = new Url(baseUrl);
            URL_REPOSITORY.save(url);
            Methods.handleFlash(ctx, "Страница успешно добавлена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
