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

    public static void showMainPage(Context ctx) {
        Base page = new Base();
        String flash = Methods.getFlash(ctx);
        page.setFlash(flash != null ? flash : "Добавьте URL");
        page.setColor("info");
        ctx.status(200);
        ctx.render("index.jte", Map.of("page", page));
    }

    public static void showUrlList(Context ctx) throws SQLException {
        List<Url> urls = UrlRepository.findAll();
        Map<Url, UrlCheck> urlsWithLastChecks = urls.stream()
                .collect(Collectors.toMap(
                        u -> u,
                        u -> {
                            try {
                                return UrlCheckRepository.findLastCheckByUrlId(u.getId()).orElse(null);
                            } catch (SQLException e) {
                                return null;
                            }
                        }
                ));

        UrlsDto page = new UrlsDto(urlsWithLastChecks);
        page.setFlash(Methods.getFlash(ctx));
        page.setColor("info");
        ctx.status(200);
        ctx.render("urls.jte", Map.of("page", page));
    }

    public static void showUrl(Context ctx) throws SQLException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        var urlOpt = UrlRepository.findById(id);

        if (urlOpt.isEmpty()) {
            ctx.status(404);
            return;
        }

        Url url = urlOpt.get();
        List<UrlCheck> checks = UrlCheckRepository.findByUrlId(id);

        UrlDto page = new UrlDto(url, checks);
        page.setFlash(Methods.getFlash(ctx));
        page.setColor("info");
        ctx.status(200);
        ctx.render("url.jte", Map.of("page", page));
    }

    public static void createUrl(Context ctx) {
        String inputUrl = ctx.formParam("url");

        if (inputUrl == null || inputUrl.isBlank()) {
            Methods.handleFlash(ctx, "Добавьте URL", "danger", "/");
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
            Methods.handleFlash(ctx, "Некорректный URL", "danger", "/");
            return;
        }

        try {
            if (UrlRepository.exists(baseUrl)) {
                Methods.handleFlash(ctx, "Страница уже существует", "warning", "/");
                return;
            }

            Url url = new Url(baseUrl);
            UrlRepository.save(url);

            Methods.handleFlash(ctx, "Страница успешно добавлена", "success", "/urls/" + url.getId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
