package gg.jte.generated.precompiled;
import hexlet.code.AppPaths;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,13,13,13,15,15,17,17,17,17,17,17,17,17,17,23,23,23,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, s String) {
		jteOutput.writeContent("\r\n<!DOCTYPE html>\r\n<html>\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <title>Главная</title>\r\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n</head>\r\n<body class=\"container mt-5\">\r\n    <h1>Добавьте URL</h1>\r\n\r\n");
		if (flash != null) {
			jteOutput.writeContent("    <div class=\"alert alert-success\">@flash</div>\r\n");
		}
		jteOutput.writeContent("    <form");
		var __jte_html_attribute_0 = AppPaths.urls();
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
			jteOutput.writeContent(" action=\"");
			jteOutput.setContext("form", "action");
			jteOutput.writeUserContent(__jte_html_attribute_0);
			jteOutput.setContext("form", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent(" method=\"POST\">\r\n        <input type=\"text\" name=\"url\" class=\"form-control mb-2\" placeholder=\"https://example.com\" required>\r\n        <button type=\"submit\" class=\"btn btn-primary\">Добавить</button>\r\n    </form>\r\n</body>\r\n</html>\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		s String = (s)params.getOrDefault("String", lash);
		render(jteOutput, jteHtmlInterceptor, String);
	}
}
