package gg.jte.generated.precompiled;
import hexlet.code.AppPaths;
public final class JteUrlGenerated {
	public static final String JTE_NAME = "Url.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,15,15,15,17,17,24,24,36,36,44,44,47,47,49,49,51,51,51,51,51,51,51,51,51,56,56,56,56,56,56,56,56,56,61,61,61,1,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, s hexlet.code.models.Url, s String) {
		jteOutput.writeContent("\r\n<!DOCTYPE html>\r\n<html>\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <title>Информация о URL</title>\r\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n</head>\r\n<body class=\"container mt-5\">\r\n\r\n<h1>URL: @url.getName()</h1>\r\n\r\n");
		if (flash != null && !flash.isEmpty()) {
			jteOutput.writeContent("<div class=\"alert alert-success\">@flash</div>\r\n");
		}
		jteOutput.writeContent("<div class=\"mb-3\">\r\n    <strong>ID:</strong> @url.getId()\r\n</div>\r\n\r\n<h3>Проверки</h3>\r\n");
		if (url.getChecks() != null && !url.getChecks().isEmpty()) {
			jteOutput.writeContent("<table class=\"table table-bordered\">\r\n    <thead>\r\n        <tr>\r\n            <th>Дата</th>\r\n            <th>Статус</th>\r\n            <th>Title</th>\r\n            <th>H1</th>\r\n            <th>Description</th>\r\n        </tr>\r\n    </thead>\r\n    <tbody>\r\n");
			for (check in url.getChecks()) {
				jteOutput.writeContent("    <tr>\r\n        <td>@Methods.timeFormat(check.getCreatedAt())</td>\r\n        <td>@check.getStatusCode()</td>\r\n        <td>@(check.getTitle() != null && !check.getTitle().isEmpty() ? check.getTitle() : \"-\")</td>\r\n        <td>@(check.getH1() != null && !check.getH1().isEmpty() ? check.getH1() : \"-\")</td>\r\n        <td>@(check.getDescription() != null && !check.getDescription().isEmpty() ? check.getDescription() : \"-\")</td>\r\n    </tr>\r\n");
			}
			jteOutput.writeContent("    </tbody>\r\n</table>\r\n");
		} else {
			jteOutput.writeContent("<p>Нет проверок</p>\r\n");
		}
		jteOutput.writeContent("<form");
		var __jte_html_attribute_0 = AppPaths.urlChecks(url.getId());
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
			jteOutput.writeContent(" action=\"");
			jteOutput.setContext("form", "action");
			jteOutput.writeUserContent(__jte_html_attribute_0);
			jteOutput.setContext("form", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent(" method=\"POST\">\r\n    <button type=\"submit\" class=\"btn btn-primary\">Проверить URL</button>\r\n</form>\r\n\r\n<div class=\"mt-3\">\r\n    <a");
		var __jte_html_attribute_1 = AppPaths.urls();
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_1)) {
			jteOutput.writeContent(" href=\"");
			jteOutput.setContext("a", "href");
			jteOutput.writeUserContent(__jte_html_attribute_1);
			jteOutput.setContext("a", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent(" class=\"btn btn-secondary\">Назад к списку URL</a>\r\n</div>\r\n\r\n</body>\r\n</html>\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		s hexlet.code.models.Url = (s)params.getOrDefault("hexlet.code.models.Url", rl);
		s String = (s)params.getOrDefault("String", lash);
		render(jteOutput, jteHtmlInterceptor, hexlet.code.models.Url, String);
	}
}
