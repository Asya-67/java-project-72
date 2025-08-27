package gg.jte.generated.precompiled;
import hexlet.code.AppPaths;
public final class JteurlsGenerated {
	public static final String JTE_NAME = "urls.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,15,15,15,17,17,30,30,35,35,37,37,39,39,42,42,44,44,46,46,49,49,49,49,49,49,49,49,49,50,50,50,50,50,50,50,50,50,55,55,61,61,61,1,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, s List<hexlet.code.models.Url>, s String) {
		jteOutput.writeContent("\r\n<!DOCTYPE html>\r\n<html>\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <title>Список URL</title>\r\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n</head>\r\n<body class=\"container mt-5\">\r\n\r\n<h1>Список URL</h1>\r\n\r\n");
		if (flash != null && !flash.isEmpty()) {
			jteOutput.writeContent("<div class=\"alert alert-success\">@flash</div>\r\n");
		}
		jteOutput.writeContent("<table class=\"table table-bordered\">\r\n    <thead>\r\n        <tr>\r\n            <th>ID</th>\r\n            <th>URL</th>\r\n            <th>Последняя проверка</th>\r\n            <th>Статус</th>\r\n            <th>Действия</th>\r\n        </tr>\r\n    </thead>\r\n    <tbody>\r\n");
		for (url in urls) {
			jteOutput.writeContent("        <tr>\r\n            <td>@url.getId()</td>\r\n            <td>@url.getName()</td>\r\n            <td>\r\n");
			if (url.getChecks() != null && !url.getChecks().isEmpty()) {
				jteOutput.writeContent("                @url.getChecks().get(url.getChecks().size() - 1).getCreatedAt()\r\n");
			} else {
				jteOutput.writeContent("                -\r\n");
			}
			jteOutput.writeContent("            </td>\r\n            <td>\r\n");
			if (url.getChecks() != null && !url.getChecks().isEmpty()) {
				jteOutput.writeContent("                @url.getChecks().get(url.getChecks().size() - 1).getStatusCode()\r\n");
			} else {
				jteOutput.writeContent("                -\r\n");
			}
			jteOutput.writeContent("            </td>\r\n            <td>\r\n                <a");
			var __jte_html_attribute_0 = AppPaths.url(url.getId());
			if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
				jteOutput.writeContent(" href=\"");
				jteOutput.setContext("a", "href");
				jteOutput.writeUserContent(__jte_html_attribute_0);
				jteOutput.setContext("a", null);
				jteOutput.writeContent("\"");
			}
			jteOutput.writeContent(" class=\"btn btn-sm btn-info\">Посмотреть</a>\r\n                <form");
			var __jte_html_attribute_1 = AppPaths.urlChecks(url.getId());
			if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_1)) {
				jteOutput.writeContent(" action=\"");
				jteOutput.setContext("form", "action");
				jteOutput.writeUserContent(__jte_html_attribute_1);
				jteOutput.setContext("form", null);
				jteOutput.writeContent("\"");
			}
			jteOutput.writeContent(" method=\"POST\" style=\"display:inline\">\r\n                    <button type=\"submit\" class=\"btn btn-sm btn-primary\">Проверить</button>\r\n                </form>\r\n            </td>\r\n        </tr>\r\n");
		}
		jteOutput.writeContent("    </tbody>\r\n</table>\r\n\r\n</body>\r\n</html>\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		s List<hexlet.code.models.Url> = (s)params.getOrDefault("List<hexlet.code.models.Url>", rls);
		s String = (s)params.getOrDefault("String", lash);
		render(jteOutput, jteHtmlInterceptor, List<hexlet.code.models.Url>, String);
	}
}
