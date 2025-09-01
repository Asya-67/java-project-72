package hexlet.code;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.http.Context;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Methods {

    public static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver resolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(resolver, ContentType.Html);
    }

    public static String getJdbcUrl() {
        return System.getenv().getOrDefault(
                "JDBC_DATABASE_URL",
                "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;"
        );
    }

    public static int getPort() {
        return Integer.parseInt(System.getenv().getOrDefault("PORT", "7000"));
    }

    public static void handleFlash(Context ctx, String message, String type, String redirectPath) {
        setFlash(ctx, message, type);
        ctx.redirect(redirectPath);
    }

    public static void handleFlash(Context ctx, String message, String type) {
        handleFlash(ctx, message, type, "/urls");
    }

    public static void setFlash(Context ctx, String message, String type) {
        ctx.sessionAttribute("flash", message);
        ctx.sessionAttribute("flash_type", type);
    }

    public static void setFlash(Context ctx, String message) {
        setFlash(ctx, message, "info");
    }

    public static String getFlash(Context ctx) {
        return ctx.sessionAttribute("flash");
    }

    public static String getFlashType(Context ctx) {
        return ctx.sessionAttribute("flash_type");
    }

    public static Timestamp toTimestamp(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }

    public static String timeFormat(Timestamp createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return formatter.format(createdAt.toLocalDateTime());
    }
}
