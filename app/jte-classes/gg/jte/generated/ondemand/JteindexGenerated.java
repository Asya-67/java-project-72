package gg.jte.generated.ondemand;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,11,11,11,12,12,12,13,13,23,23,23,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String flash) {
		jteOutput.writeContent("<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <title>Добавить сайт</title>\r\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n</head>\r\n<body class=\"container mt-5\">\r\n<h1 class=\"text-center text-primary\">Добавить сайт</h1>\r\n\r\n");
		if (flash != null) {
			jteOutput.writeContent("\r\n    <div class=\"alert alert-info\">");
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(flash);
			jteOutput.writeContent("</div>\r\n");
		}
		jteOutput.writeContent("\r\n\r\n<form action=\"/urls\" method=\"post\" class=\"mb-3\">\r\n    <input type=\"text\" name=\"url\" class=\"form-control mb-2\" placeholder=\"https://example.com\">\r\n    <button type=\"submit\" class=\"btn btn-primary\">Добавить</button>\r\n</form>\r\n\r\n<a href=\"/urls\">Список сайтов</a>\r\n</body>\r\n</html>\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String flash = (String)params.get("flash");
		render(jteOutput, jteHtmlInterceptor, flash);
	}
}
