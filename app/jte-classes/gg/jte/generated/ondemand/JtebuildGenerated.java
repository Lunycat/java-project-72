package gg.jte.generated.ondemand;
import hexlet.code.dto.BasePage;
@SuppressWarnings("unchecked")
public final class JtebuildGenerated {
	public static final String JTE_NAME = "build.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,2,4,4,4,4,24,24,24,24,24,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, BasePage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, page, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n    <div class=\"container-fluid bg-primary-subtle p-5\">\r\n        <div class=\"col-md-10 col-lg-8 mx-auto text-white\">\r\n            <h1>Анализатор страниц</h1>\r\n            <p>Проверяйте сайты на СЕО пригодность</p>\r\n            <form action=\"/urls\" method=\"post\" class=\"rss-form text-body\">\r\n                <div class=\"row\">\r\n                    <div class=\"col\">\r\n                        <div class=\"form-floating\">\r\n                            <input id=\"url-input\" autofocus=\"\" type=\"text\" required=\"\" name=\"url\" aria-label=\"url\" class=\"form-control w-100\" placeholder=\"ссылка\" autocomplete=\"off\">\r\n                            <label for=\"url-input\">Ссылка</label>\r\n                        </div>\r\n                    </div>\r\n                    <div class=\"col-auto\">\r\n                        <button type=\"submit\" class=\"h-100 btn btn-lg btn-primary px-sm-5\">Проверить</button>\r\n                    </div>\r\n                </div>\r\n            </form>\r\n        </div>\r\n    </div>\r\n");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		BasePage page = (BasePage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
