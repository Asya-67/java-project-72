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
            Methods.handleFlash(ctx, "Ошибка при получении сайта из базы данных", "danger");
            ctx.redirect("/urls/" + urlId);
            return;
        }

        if (url == null) {
            ctx.status(404);
            return;
        }

        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            Document doc = Jsoup.parse(response.getBody());

            UrlCheck check = new UrlCheck();
            check.setUrlId(urlId);
            check.setStatusCode(response.getStatus());
            check.setCreatedAt(Methods.toTimestamp(LocalDateTime.now()));
            check.setTitle(doc.selectFirst("title") != null ? doc.selectFirst("title").text() : "");
            check.setH1(doc.selectFirst("h1") != null ? doc.selectFirst("h1").text() : "");
            check.setDescription(doc.selectFirst("meta[name=description]") != null
                    ? doc.selectFirst("meta[name=description]").attr("content") : "");

            UrlCheckRepository.save(check);

            Methods.handleFlash(ctx, "Проверка успешно добавлена", "success");
            ctx.redirect("/urls/" + urlId);

        } catch (Exception e) {
            Methods.handleFlash(ctx, "Ошибка при проверке сайта", "danger");
            ctx.redirect("/urls/" + urlId);
        }
    }
}
