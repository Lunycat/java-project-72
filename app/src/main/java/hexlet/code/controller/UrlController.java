package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;

import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {

    public static void build(Context ctx) {
        BasePage page = new BasePage();
        page.setMessage(ctx.consumeSessionAttribute("message"));
        page.setMode(ctx.consumeSessionAttribute("mode"));
        ctx.render("build.jte", model("page", page));
    }

    public static void create(Context ctx) {
        try {
            String urlFromForm = ctx.formParam("url");
            URI uri = new URI(urlFromForm);
            URL convertedUrl = uri.toURL();

            Url url = new Url(convertedUrl.getProtocol() + "://" + convertedUrl.getAuthority());
            Url duplicateUrl = UrlRepository
                    .findName(url.getName())
                    .orElseGet(null);

            if (duplicateUrl != null) {
                ctx.sessionAttribute("message", "Страница успешно добавлена");
                ctx.sessionAttribute("mode", "success");

                ctx.redirect("/");
            }

            UrlRepository.save(url);
            ctx.sessionAttribute("message", "Страница успешно добавлена");
            ctx.sessionAttribute("mode", "success");
            ctx.redirect(NamedRoutes.urlsPath());

        } catch (Exception e) {
            ctx.sessionAttribute("message", "Некорректный URL");
            ctx.sessionAttribute("mode", "danger");

            ctx.redirect("/");
        }
    }

    public static void index(Context ctx) throws SQLException {
        List<Url> urls = UrlRepository.getEntities();
        UrlsPage page = new UrlsPage(urls);
        page.setMessage(ctx.consumeSessionAttribute("message"));
        page.setMode(ctx.consumeSessionAttribute("mode"));
        ctx.render("index.jte", model("page", page));
    }

    public static void show(Context ctx) {

    }
}
