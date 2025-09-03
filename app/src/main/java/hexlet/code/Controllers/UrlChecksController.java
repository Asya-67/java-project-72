package hexlet.code.controllers;

import hexlet.code.Methods;
import hexlet.code.constants.FlashColors;
import hexlet.code.constants.FlashMessages;
import hexlet.code.models.Url;
import hexlet.code.models.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class UrlChecksController {

    public static void makeCheck(Context ctx) {
        Long urlId = ctx.pathParamAsClass("id", Long.class).get();

        Url url = getUrlOrFlash(ctx, urlId);
        if (url == null) {
            return;
        }

        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            int statusCode = response.getStatus();
            String body = response.getBody() != null ? response.getBody() : "";

            Document doc = Jsoup.parse(body);

            String title = doc.selectFirst("title") != null ? doc.selectFirst("title").text() : "";
            String h1 = doc.selectFirst("h1") != null ? doc.selectFirst("h1").text() : "";
            String description = doc.selectFirst("meta[name=description]") != null
                    ? doc.selectFirst("meta[name=description]").attr("content")
                    : "";

            UrlCheck check = new UrlCheck();
            check.setUrlId(urlId);
            check.setStatusCode(statusCode);
            check.setTitle(title);
            check.setH1(h1);
            check.setDescription(description);
            check.setCreatedAt(Methods.toTimestamp(LocalDateTime.now()));

            UrlCheckRepository.save(check);

            Methods.handleFlash(ctx, FlashMessages.CHECK_ADDED, FlashColors.SUCCESS, urlPath(urlId));

        } catch (Exception e) {
            Methods.handleFlash(ctx, FlashMessages.CHECK_ERROR, FlashColors.DANGER, urlPath(urlId));
        }
    }

    private static Url getUrlOrFlash(Context ctx, Long urlId) {
        try {
            Url url = UrlRepository.findById(urlId).orElse(null);
            if (url == null) {
                Methods.handleFlash(ctx, FlashMessages.URL_NOT_FOUND, FlashColors.DANGER, "/urls");
            }
            return url;
        } catch (SQLException e) {
            Methods.handleFlash(ctx, FlashMessages.DB_ERROR, FlashColors.DANGER, urlPath(urlId));
            return null;
        }
    }

    private static String urlPath(Long id) {
        return "/urls/" + id;
    }
}
