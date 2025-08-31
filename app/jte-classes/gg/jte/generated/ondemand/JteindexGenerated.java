package gg.jte.generated.ondemand;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {6,6,6,6,6,6,6,7,7,7,7,8,8,8,10,10,32,32,32,32,32,32};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		jteOutput.writeContent("@content commonsite(content, page) {\r\n    <div class=\"container-fluid bg-dark p-5\">\r\n        <div class=\"row\">\r\n            <div class=\"col-md-10 col-lg-8 mx-auto text-white\">\r\n                <h1 class=\"display-3 mb-0\">Добавьте URL</h1>\r\n\r\n                ");
		if (page != null && page.getFlash() != null) {
			jteOutput.writeContent("\r\n                    <div class=\"alert alert-");
			jteOutput.setContext("div", "class");
			jteOutput.writeUserContent(page.getColor());
			jteOutput.setContext("div", null);
			jteOutput.writeContent(" mt-3\">\r\n                        ");
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(page.getFlash());
			jteOutput.writeContent("\r\n                    </div>\r\n                ");
		}
		jteOutput.writeContent("\r\n\r\n                <form action=\"/urls\" method=\"post\" class=\"rss-form text-body\">\r\n                    <div class=\"row\">\r\n                        <div class=\"col\">\r\n                            <div class=\"form-floating\">\r\n                                <input id=\"url-input\" autofocus type=\"text\" required name=\"url\"\r\n                                       aria-label=\"url\" class=\"form-control w-100\" placeholder=\"ссылка\" autocomplete=\"off\">\r\n                                <label for=\"url-input\">Ссылка</label>\r\n                            </div>\r\n                        </div>\r\n                        <div class=\"col-auto\">\r\n                            <button type=\"submit\" class=\"h-100 btn btn-lg btn-primary px-sm-5\">Проверить</button>\r\n                        </div>\r\n                    </div>\r\n                </form>\r\n\r\n                <p class=\"mt-2 mb-0 text-muted\">Пример: https://www.example.com</p>\r\n            </div>\r\n        </div>\r\n    </div>\r\n}\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
