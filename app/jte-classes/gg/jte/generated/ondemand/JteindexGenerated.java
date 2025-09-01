package gg.jte.generated.ondemand;
import hexlet.code.dto.Base;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,8,8,8,9,9,9,9,10,10,10,13,13,23,23,23,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Base page) {
		jteOutput.writeContent("\r\n@@extends(\"layout.jte\")\r\n@section content {\r\n    <div class=\"container mt-4\">\r\n        <h1>Добавьте URL</h1>\r\n\r\n        ");
		if (page != null && page.getFlash() != null) {
			jteOutput.writeContent("\r\n            <div class=\"alert alert-");
			jteOutput.setContext("div", "class");
			jteOutput.writeUserContent(page.getColor());
			jteOutput.setContext("div", null);
			jteOutput.writeContent(" alert-dismissible fade show\" role=\"alert\">\r\n                ");
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(page.getFlash());
			jteOutput.writeContent("\r\n                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n            </div>\r\n        ");
		}
		jteOutput.writeContent("\r\n\r\n        <form method=\"post\" action=\"/urls\" class=\"mt-3\">\r\n            <div class=\"mb-3\">\r\n                <input type=\"text\" name=\"url\" class=\"form-control\" placeholder=\"Введите URL\">\r\n            </div>\r\n            <button type=\"submit\" class=\"btn btn-primary\">Добавить</button>\r\n        </form>\r\n    </div>\r\n}\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Base page = (Base)params.getOrDefault("page", null);
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
