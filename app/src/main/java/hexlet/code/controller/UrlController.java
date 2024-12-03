package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URL;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;

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
                setSessionAttributeFacade(ctx, "primary");
                ctx.redirect(NamedRoutes.urlsPath());
            } else {
                UrlRepository.save(url);
                setSessionAttributeFacade(ctx, "success");
                ctx.redirect(NamedRoutes.urlsPath());
            }

        } catch (Exception e) {
            setSessionAttributeFacade(ctx, "danger");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    public static void index(Context ctx) throws SQLException {
        List<Url> urls = UrlRepository.getEntities();
        Map<Long, UrlCheck> urlChecks = UrlCheckRepository.findLastChecks();
        UrlsPage page = new UrlsPage(urls, urlChecks);
        initializationPageFacade(page, ctx);

        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Not found URL"));
        UrlCheckRepository.setUrlChecks(url);
        UrlPage page = new UrlPage(url);
        initializationPageFacade(page, ctx);

        ctx.render("urls/show.jte", model("page", page));
    }

    private static void initializationPageFacade(BasePage page, Context ctx) {
        page.getFlash().put("message", ctx.consumeSessionAttribute("message"));
        page.getFlash().put("mode", ctx.consumeSessionAttribute("mode"));
        page.getFlash().put("svg", ctx.consumeSessionAttribute("svg"));
    }

    private static void setSessionAttributeFacade(Context ctx, String mode) {
        switch (mode) {
            case "success" -> {
                ctx.sessionAttribute("message", "Страница успешно добавлена");
                ctx.sessionAttribute("mode", "success");
                ctx.sessionAttribute("svg", "<svg class=\"bi flex-shrink-0 me-2\" width=\"24\" height=\"24\" "
                        + "role=\"img\" aria-label=\"Success:\"><use xlink:href=\"#check-circle-fill\"/></svg>");
            }
            case "danger" -> {
                ctx.sessionAttribute("message", "Некорректный URL");
                ctx.sessionAttribute("mode", "danger");
                ctx.sessionAttribute("svg", "<svg class=\"bi flex-shrink-0 me-2\" width=\"24\" height=\"24\" "
                        + "role=\"img\" aria-label=\"Danger:\"><use xlink:href=\"#exclamation-triangle-fill\"/></svg>");
            }
            case "primary" -> {
                ctx.sessionAttribute("message", "Страница уже существует");
                ctx.sessionAttribute("mode", "primary");
                ctx.sessionAttribute("svg", "<svg class=\"bi flex-shrink-0 me-2\" width=\"24\" height=\"24\" "
                        + "role=\"img\" aria-label=\"Info:\"><use xlink:href=\"#info-fill\"/></svg>");
            }
            default -> throw new RuntimeException();
        }
    }
}
