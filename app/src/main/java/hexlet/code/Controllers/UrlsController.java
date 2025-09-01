package hexlet.code.controllers;

import hexlet.code.Methods;
import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.dto.Base;
import hexlet.code.dto.UrlDto;
import hexlet.code.dto.UrlsDto;
import io.javalin.http.Context;

import java.net.MalformedURLException;
import java.net.URL;
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

    public static void createUrl(Context ctx) throws SQLException {
        String inputUrl = ctx.formParam("url");

        if (inputUrl == null) {
            String body = ctx.body();
            if (body != null && body.startsWith("url=")) {
                inputUrl = body.substring(4);
            }
        }

        if (inputUrl != null && inputUrl.endsWith("/")) {
            inputUrl = inputUrl.substring(0, inputUrl.length() - 1);
        }

        if (inputUrl == null || !isValidUrl(inputUrl)) {
            Methods.handleFlash(ctx, "Некорректный URL", "danger", "/");
            return;
        }

        Url url = UrlRepository.findByName(inputUrl);
        if (url == null) {
            url = UrlRepository.save(new Url(inputUrl));
            Methods.handleFlash(ctx, "Страница успешно добавлена", "success", "/urls/" + url.getId());
        } else {
            Methods.handleFlash(ctx, "Страница уже существует", "info", "/urls/" + url.getId());
        }
    }

    private static boolean isValidUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
