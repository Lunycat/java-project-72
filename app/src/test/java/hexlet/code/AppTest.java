package hexlet.code;

import java.io.IOException;

import java.sql.SQLException;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import okhttp3.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    Javalin app;

    @BeforeEach
    public void beforeEach() throws SQLException, IOException {
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            Response response = client.get(NamedRoutes.rootPath());
            assertEquals(200, response.code());
        });
    }

    @Test
    public void testPostUrlSuccess() {
        JavalinTest.test(app, ((server, client) -> {
            String requestBody = "url=https://vk.com";
            Response response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertEquals(200, response.code());
            assertTrue(response.body().string().contains("https://vk.com"));
        }));
    }

    @Test
    public void testPostUrlFailed() {
        JavalinTest.test(app, ((server, client) -> {
            String requestBody = "url=https://vk.com";
            Response response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertEquals(200, response.code());
            assertTrue(response.body().string().contains("Анализатор страниц"));
        }));
    }

    @Test
    public void testShowUrl() throws SQLException {
        Url url = new Url("https://vk.com");
        UrlRepository.save(url);

        JavalinTest.test(app, ((server, client) -> {
            Response response = client.get(NamedRoutes.urlPath(url.getId()));
            assertEquals(200, response.code());
        }));
    }

    @Test
    public void testNotFound() {
        JavalinTest.test(app, ((server, client) -> {
            Response response = client.get(NamedRoutes.urlPath(99999999L));
            assertEquals(404, response.code());
        }));
    }

    @Test public void testDuplicateUrl() throws SQLException {
        Url url = new Url("https://vk.com");
        UrlRepository.save(url);

        JavalinTest.test(app, ((server, client) -> {
            String requestBody = "url=https://vk.com";
            Response response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertEquals(200, response.code());
            assertTrue(response.body().string().contains("Анализатор страниц"));
        }));
    }
}
