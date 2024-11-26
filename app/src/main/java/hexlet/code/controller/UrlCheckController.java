package hexlet.code.controller;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlRepository;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.sql.SQLException;

public class UrlCheckController {

    public static void check(Context ctx) throws SQLException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Not found URL"));
        HttpResponse<String> response = Unirest.get(url.getName()).asString();
        int statusCode = response.getStatus();

        Document document = Jsoup.parse(response.getBody());
        String title = document.title();
        Element h1Element = document.select("h1").first();
        String h1 = h1Element != null ? h1Element.text() : null;
        String description = document.select("meta[name=description]").attr("content");

        UrlCheck urlCheck = UrlCheck.builder()
                .statusCode(statusCode)
                .title(title)
                .h1(h1)
                .description(description)
                .url(url)
                .build();

    }
}
