package gg.jte.generated.ondemand;
public final class JteurlGenerated {
	public static final String JTE_NAME = "url.jte";
	public static final int[] JTE_LINE_INFO = {2,2,2,2,2,2,2,2,3,3,3,9,9,9,10,10,10,11,11,11,11,17,17,17,17,17,17};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		jteOutput.writeContent("@content commonsite(content, page) {\r\n    <div class=\"container mt-5\">\r\n        <h1>");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(page.getUrl().getName());
		jteOutput.writeContent("</h1>\r\n        <p>Добавлен: ");
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(Methods.timeFormat(page.getUrl().getCreatedAt()));
		jteOutput.writeContent("</p>\r\n\r\n        <h2>Проверки</h2>\r\n        <ul class=\"list-group\">\r\n            {{ for(var check : page.getUrlChecks()) }}\r\n                <li class=\"list-group-item\">\r\n                    Дата: ");
		jteOutput.setContext("li", null);
		jteOutput.writeUserContent(Methods.timeFormat(check.getCreatedAt()));
		jteOutput.writeContent(",\r\n                    Статус: ");
		jteOutput.setContext("li", null);
		jteOutput.writeUserContent(check.getStatus());
		jteOutput.writeContent("\r\n                    <a href=\"/urls/");
		jteOutput.setContext("a", "href");
		jteOutput.writeUserContent(page.getUrl().getId());
		jteOutput.setContext("a", null);
		jteOutput.writeContent("/checks\" class=\"btn btn-sm btn-primary ms-2\">Проверить снова</a>\r\n                </li>\r\n            {{ end }}\r\n        </ul>\r\n    </div>\r\n}\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
