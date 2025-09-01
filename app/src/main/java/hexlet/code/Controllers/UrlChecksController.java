package hexlet.code.controllers;

import hexlet.code.Methods;
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

        Url url;
        try {
            url = UrlRepository.findById(urlId).orElse(null);
        } catch (SQLException e) {
            Methods.handleFlash(ctx, "Ошибка при получении сайта из базы данных", "danger", "/urls/" + urlId);
            return;
        }

        if (url == null) {
            ctx.status(404);
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

            UrlCheck savedCheck = UrlCheckRepository.save(check);
            if (savedCheck.getId() == null) {
                throw new RuntimeException("Ошибка сохранения проверки URL");
            }

            Methods.handleFlash(ctx, "Проверка успешно добавлена", "success", "/urls/" + urlId);

        } catch (Exception e) {
            Methods.handleFlash(ctx, "Ошибка при проверке сайта", "danger", "/urls/" + urlId);
        }
    }
}
