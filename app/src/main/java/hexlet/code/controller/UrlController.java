package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import hexlet.code.util.Utils;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

@Slf4j
public class UrlController {

    public static void build(Context ctx) {
        BasePage page = new BasePage();
        initializationPageFacade(page, ctx);

        ctx.render("index.jte", model("page", page));
    }

    public static void create(Context ctx) {
        try {
            String urlFromForm = ctx.formParam("url");
            URI uri = new URI(urlFromForm);
            URL convertedUrl = uri.toURL();

            Url url = new Url(convertedUrl.getProtocol() + "://" + convertedUrl.getAuthority());
            Url duplicateUrl = UrlRepository
                    .findName(url.getName())
                    .orElse(null);

            if (duplicateUrl != null) {
                ctx.sessionAttribute("message", "Страница уже существует");
                ctx.sessionAttribute("mode", "primary");
                ctx.sessionAttribute("svg", "<svg class=\"bi flex-shrink-0 me-2\" width=\"24\" height=\"24\" "
                        + "role=\"img\" aria-label=\"Info:\"><use xlink:href=\"#info-fill\"/></svg>");

                ctx.redirect(NamedRoutes.urlsPath());
            } else {
                UrlRepository.save(url);
                ctx.sessionAttribute("message", "Страница успешно добавлена");
                ctx.sessionAttribute("mode", "success");
                ctx.sessionAttribute("svg", "<svg class=\"bi flex-shrink-0 me-2\" width=\"24\" height=\"24\" "
                        + "role=\"img\" aria-label=\"Success:\"><use xlink:href=\"#check-circle-fill\"/></svg>");

                ctx.redirect(NamedRoutes.urlsPath());
            }

        } catch (Exception e) {
            ctx.sessionAttribute("message", "Некорректный URL");
            ctx.sessionAttribute("mode", "danger");
            ctx.sessionAttribute("svg", "<svg class=\"bi flex-shrink-0 me-2\" width=\"24\" height=\"24\" "
                    + "role=\"img\" aria-label=\"Danger:\"><use xlink:href=\"#exclamation-triangle-fill\"/></svg>");

            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    public static void index(Context ctx) throws SQLException {
        List<Url> urls = UrlRepository.getEntities();
        UrlsPage page = new UrlsPage(urls);
        initializationPageFacade(page, ctx);

        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Not found URL"));
        UrlPage page = new UrlPage(url);

        ctx.render("urls/show.jte", model("page", page));
    }

    private static void initializationPageFacade(BasePage page, Context ctx) {
        page.getFlash().put("message", ctx.consumeSessionAttribute("message"));
        page.getFlash().put("mode", ctx.consumeSessionAttribute("mode"));
        page.getFlash().put("svg", ctx.consumeSessionAttribute("svg"));
    }
}
