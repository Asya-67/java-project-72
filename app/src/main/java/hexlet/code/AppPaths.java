package hexlet.code;

public class AppPaths {
    public static String root() {
        return "/";
    }

    public static String urls() {
        return "/urls";
    }

    public static String url(String id) {
        return "/urls/" + id;
    }

    public static String url(Long id) {
        return url(String.valueOf(id));
    }

    public static String urlChecks(String id) {
        return "/urls/" + id + "/checks";
    }

    public static String urlChecks(Long id) {
        return urlChecks(String.valueOf(id));
    }
}
