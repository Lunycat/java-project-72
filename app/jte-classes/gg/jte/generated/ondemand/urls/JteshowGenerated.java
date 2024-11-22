package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlPage;
@SuppressWarnings("unchecked")
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,2,4,4,4,4,10,10,10,16,16,16,20,20,20,24,24,24,51,51,51,51,51,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlPage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, page, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n    <main class=\"flex-grow-1\">\r\n\r\n        <section>\r\n\r\n            <div class=\"container-lg mt-5\">\r\n                <h1>Сайт: ");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</h1>\r\n\r\n                <table class=\"table table-bordered table-hover mt-3\">\r\n                    <tbody>\r\n                    <tr>\r\n                        <td>ID</td>\r\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</td>\r\n                    </tr>\r\n                    <tr>\r\n                        <td>Имя</td>\r\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</td>\r\n                    </tr>\r\n                    <tr>\r\n                        <td>Дата создания</td>\r\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getCreatedAt().toString());
				jteOutput.writeContent("</td>\r\n                    </tr>\r\n                    </tbody>\r\n                </table>\r\n\r\n                <h2 class=\"mt-5\">Проверки</h2>\r\n                <form method=\"post\" action=\"/urls/1/checks\">\r\n                    <button type=\"submit\" class=\"btn btn-primary\">Запустить проверку</button>\r\n                </form>\r\n\r\n                <table class=\"table table-bordered table-hover mt-3\">\r\n                    <thead>\r\n                    <th class=\"col-1\">ID</th>\r\n                    <th class=\"col-1\">Код ответа</th>\r\n                    <th>title</th>\r\n                    <th>h1</th>\r\n                    <th>description</th>\r\n                    <th class=\"col-2\">Дата проверки</th>\r\n                    </thead>\r\n                    <tbody>\r\n\r\n                    </tbody>\r\n                </table>\r\n            </div>\r\n\r\n        </section>\r\n    </main>\r\n");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage page = (UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}