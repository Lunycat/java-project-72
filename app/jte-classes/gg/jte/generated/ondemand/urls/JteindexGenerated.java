package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.UrlCheck;
import hexlet.code.util.NamedRoutes;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,4,4,4,4,6,6,6,6,23,23,25,25,25,26,26,26,26,26,26,26,26,26,26,26,26,28,28,29,29,29,30,30,33,33,34,34,34,35,35,38,38,43,43,43,43,43,4,4,4,4};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, page, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n    <div class=\"container-lg mt-5\">\r\n        <h1>Сайты</h1>\r\n\r\n        <table class=\"table table-bordered border-primary\">\r\n            <thead>\r\n\r\n            <tr>\r\n                <th class=\"col-1\">ID</th>\r\n                <th>Имя</th>\r\n                <th class=\"col-2\">Последняя проверка</th>\r\n                <th class=\"col-1\">Код ответа</th>\r\n            </tr>\r\n\r\n            </thead>\r\n            <tbody>\r\n\r\n            ");
				for (var url : page.getUrls()) {
					jteOutput.writeContent("\r\n                <tr>\r\n                    <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getId());
					jteOutput.writeContent("</td>\r\n                    <td><a");
					var __jte_html_attribute_0 = NamedRoutes.urlPath(url.getId());
					if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
						jteOutput.writeContent(" href=\"");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(__jte_html_attribute_0);
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\"");
					}
					jteOutput.writeContent(">");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(url.getName());
					jteOutput.writeContent("</a></td>\r\n                    <td>\r\n                        ");
					if (!url.getUrlCheckList().isEmpty()) {
						jteOutput.writeContent("\r\n                            ");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(url.getUrlCheckList().get(0).getCreatedAt());
						jteOutput.writeContent("\r\n                        ");
					}
					jteOutput.writeContent("\r\n                    </td>\r\n                    <td>\r\n                        ");
					if (!url.getUrlCheckList().isEmpty()) {
						jteOutput.writeContent("\r\n                            ");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(url.getUrlCheckList().get(0).getStatusCode());
						jteOutput.writeContent("\r\n                        ");
					}
					jteOutput.writeContent("\r\n                    </td>\r\n                </tr>\r\n            ");
				}
				jteOutput.writeContent("\r\n\r\n            </tbody>\r\n        </table>\r\n    </div>\r\n");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
