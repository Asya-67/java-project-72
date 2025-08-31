package hexlet.code.controllers;

import hexlet.code.Methods;
import hexlet.code.dto.Base;
import hexlet.code.dto.UrlDto;
import hexlet.code.dto.UrlsDto;
import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UrlsController {

    private static final UrlRepository URL_REPOSITORY = new UrlRepository();
    private static final UrlCheckRepository CHECK_REPOSITORY = new UrlCheckRepository();

    public static void showMainPage(Context ctx) {
        Base page = new Base();
        page.setFlash(Methods.getFlash(ctx));
        ctx.render("index.jte", Map.of("page", page));
    }

    public static void showUrlList(Context ctx) throws SQLException {
        List<Url> urls = URL_REPOSITORY.findAll();

        Map<Url, UrlCheck> urlsWithChecks = urls.stream()
                .collect(Collectors.toMap(
                        u -> u,
                        u -> {
                            try {
                                List<UrlCheck> checks = CHECK_REPOSITORY.findByUrlId(u.getId());
                                return checks.isEmpty() ? null : checks.get(checks.size() - 1);
                            } catch (SQLException e) {
                                return null;
                            }
                        }
                ));

        UrlsDto page = new UrlsDto(urlsWithChecks);
        page.setFlash(Methods.getFlash(ctx));
        ctx.render("urls.jte", Map.of("page", page));
    }

    public static void showUrl(Context ctx) throws SQLException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        var urlOpt = URL_REPOSITORY.findById(id);

        if (urlOpt.isEmpty()) {
            ctx.status(404);
            return;
        }

        Url url = urlOpt.get();
        List<UrlCheck> checks = CHECK_REPOSITORY.findByUrlId(id);

        UrlDto page = new UrlDto(url, checks);
        page.setFlash(Methods.getFlash(ctx));
        ctx.render("url.jte", Map.of("page", page, "url", url, "checks", checks));
    }

    public static void createUrl(Context ctx) {
        String inputUrl = ctx.formParam("url");
        Base page = new Base();

        if (inputUrl == null || inputUrl.isBlank()) {
            page.setFlash("Добавьте URL");
            page.setColor("danger");
            ctx.render("index.jte", Map.of("page", page));
            return;
        }

        String baseUrl;
        try {
            URI uri = new URI(inputUrl);
            var url = uri.toURL();
            baseUrl = url.getProtocol() + "://" + url.getHost();
            if (url.getPort() != -1) {
                baseUrl += ":" + url.getPort();
            }
        } catch (Exception e) {
            page.setFlash("Некорректный URL");
            page.setColor("danger");
            ctx.render("index.jte", Map.of("page", page));
            return;
        }

        try {
            if (URL_REPOSITORY.exists(baseUrl)) {
                page.setFlash("Страница уже существует");
                page.setColor("warning");
                ctx.render("index.jte", Map.of("page", page));
                return;
            }

            Url url = new Url(baseUrl);
            URL_REPOSITORY.save(url);

            page.setFlash("Страница успешно добавлена");
            page.setColor("success");
            ctx.redirect("/urls/" + url.getId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
