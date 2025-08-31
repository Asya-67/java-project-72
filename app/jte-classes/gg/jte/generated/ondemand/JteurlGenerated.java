package gg.jte.generated.ondemand;
import hexlet.code.Methods;
import hexlet.code.AppPaths;
public final class JteurlGenerated {
	public static final String JTE_NAME = "url.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,19,19,19,19,19,19,19,19,19,19};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		jteOutput.writeContent("@content commonsite(content, page) {\r\n    <div class=\"container mt-5\">\r\n        <h1>{{ url.getName() }}</h1>\r\n        <p>Добавлен: {{ Methods.timeFormat(url.getCreatedAt()) }}</p>\r\n\r\n        <h2>Проверки</h2>\r\n        <ul class=\"list-group\">\r\n            {{ for(var check : checks) }}\r\n                <li class=\"list-group-item\">\r\n                    Дата: {{ Methods.timeFormat(check.getCreatedAt()) }},\r\n                    Статус: {{ check.getStatus() }}\r\n                    <a href=\"{{ AppPaths.urlChecksPath(url.getId()) }}\" class=\"btn btn-sm btn-primary ms-2\">Проверить снова</a>\r\n                </li>\r\n            {{ end }}\r\n        </ul>\r\n    </div>\r\n}\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
