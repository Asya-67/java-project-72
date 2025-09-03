package hexlet.code.controllers;

import hexlet.code.Methods;
import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.dto.UrlDto;
import hexlet.code.dto.UrlsDto;
import hexlet.code.constants.FlashColors;
import hexlet.code.constants.FlashMessages;
import io.javalin.http.Context;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UrlsController {

    public static void showMainPage(Context ctx) {
        var page = new UrlsDto(new HashMap<>());
        String flash = Methods.getFlash(ctx);
        page.setFlash(flash != null ? flash : "Добавьте URL");
        page.setColor(FlashColors.INFO);

        ctx.status(200).render("index.jte", Map.of("page", page));
    }

    public static void showUrlList(Context ctx) throws SQLException {
        List<Url> urls = UrlRepository.findAll();

        Map<Url, UrlCheck> urlsWithLastChecks = new HashMap<>();
        for (Url url : urls) {
            UrlCheck lastCheck = UrlCheckRepository.findLastCheckByUrlId(url.getId()).orElse(null);
            urlsWithLastChecks.put(url, lastCheck);
        }

        var page = new UrlsDto(urlsWithLastChecks);
        page.setFlash(Methods.getFlash(ctx));
        page.setColor(FlashColors.INFO);

        ctx.status(200).render("urls.jte", Map.of("page", page));
    }

    public static void showUrl(Context ctx) throws SQLException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Optional<Url> urlOpt = UrlRepository.findById(id);

        if (urlOpt.isEmpty()) {
            ctx.status(404);
            return;
        }

        Url url = urlOpt.get();
        List<UrlCheck> checks = UrlCheckRepository.findByUrlId(id);

        var page = new UrlDto(url, checks);
        page.setFlash(Methods.getFlash(ctx));
        page.setColor(FlashColors.INFO);

        ctx.status(200).render("url.jte", Map.of("page", page));
    }

    public static void createUrl(Context ctx) throws SQLException {
        String inputUrl = extractUrl(ctx);
        if (inputUrl == null || inputUrl.isBlank()) {
            Methods.handleFlash(ctx, FlashMessages.INVALID_URL, FlashColors.DANGER, "/");
            return;
        }

        inputUrl = inputUrl.trim();

        URL parsedUrl;
        try {
            parsedUrl = new URL(inputUrl);
        } catch (MalformedURLException e) {
            Methods.handleFlash(ctx, FlashMessages.INVALID_URL, FlashColors.DANGER, "/");
            return;
        }

        String normalizedUrl = String.format(
                "%s://%s%s",
                parsedUrl.getProtocol(),
                parsedUrl.getHost(),
                parsedUrl.getPort() == -1 ? "" : ":" + parsedUrl.getPort()
        ).toLowerCase();

        Url existingUrl = UrlRepository.findByName(normalizedUrl);
        if (existingUrl != null) {
            Methods.handleFlash(ctx, FlashMessages.URL_ALREADY_EXISTS, FlashColors.INFO,
                    "/urls/" + existingUrl.getId());
        } else {
            Url newUrl = UrlRepository.save(new Url(normalizedUrl));
            Methods.handleFlash(ctx, FlashMessages.URL_ADDED, FlashColors.SUCCESS,
                    "/urls/" + newUrl.getId());
        }
    }

    private static String extractUrl(Context ctx) {
        String inputUrl = ctx.formParam("url");
        if (inputUrl == null) {
            String body = ctx.body();
            if (body != null && body.startsWith("url=")) {
                inputUrl = body.substring(4);
            }
        }
        if (inputUrl != null) {
            inputUrl = inputUrl.trim();
            if (inputUrl.endsWith("/")) {
                inputUrl = inputUrl.substring(0, inputUrl.length() - 1);
            }
        }
        return inputUrl;
    }
}
