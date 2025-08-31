package hexlet.code;

public class AppPaths {

    public static String mainPath() {
        return "/";
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String url(Long id) {
        return "/urls/" + id;
    }

    public static String url(String id) {
        return "/urls/" + id;
    }

    public static String urlChecks(Long id) {
        return "/urls/" + id + "/checks";
    }

    public static String urlChecks(String id) {
        return "/urls/" + id + "/checks";
    }
}
