package gg.jte.generated.ondemand;
import hexlet.code.dto.UrlDto;
public final class JteurlGenerated {
	public static final String JTE_NAME = "url.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,6,6,6,6,19,19,20,20,22,22,22,23,23,23,24,24,24,25,25,25,26,26,26,27,27,27,29,29,30,30,34,34,40,40,40,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlDto page) {
		jteOutput.writeContent("\r\n@extends(\"layout.jte\")\r\n@section content {\r\n    <div class=\"container mt-4\">\r\n        <h1>Страница: ");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(page.getUrl().getName());
		jteOutput.writeContent("</h1>\r\n        <table class=\"table table-bordered mt-3\">\r\n            <thead>\r\n                <tr>\r\n                    <th>ID</th>\r\n                    <th>Status Code</th>\r\n                    <th>Title</th>\r\n                    <th>H1</th>\r\n                    <th>Description</th>\r\n                    <th>Created At</th>\r\n                </tr>\r\n            </thead>\r\n            <tbody>\r\n                ");
		if (page.getChecks() != null && !page.getChecks().isEmpty()) {
			jteOutput.writeContent("\r\n                    ");
			for (check : page.getChecks()) {
				jteOutput.writeContent("\r\n                        <tr>\r\n                            <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getId());
				jteOutput.writeContent("</td>\r\n                            <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getStatusCode());
				jteOutput.writeContent("</td>\r\n                            <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getTitle());
				jteOutput.writeContent("</td>\r\n                            <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getH1());
				jteOutput.writeContent("</td>\r\n                            <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getDescription());
				jteOutput.writeContent("</td>\r\n                            <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getCreatedAt());
				jteOutput.writeContent("</td>\r\n                        </tr>\r\n                    ");
			}
			jteOutput.writeContent("\r\n                ");
		} else {
			jteOutput.writeContent("\r\n                    <tr>\r\n                        <td colspan=\"6\" class=\"text-center\">Проверки отсутствуют</td>\r\n                    </tr>\r\n                ");
		}
		jteOutput.writeContent("\r\n            </tbody>\r\n        </table>\r\n        <a href=\"/urls\" class=\"btn btn-secondary mt-3\">Назад к списку URL</a>\r\n    </div>\r\n}\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlDto page = (UrlDto)params.getOrDefault("page", null);
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
