package hexlet.code;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.sql.SQLException;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    private static Javalin app;
    private static MockWebServer mockWebServer;

    @BeforeEach
    public void beforeEach() throws SQLException, IOException {
        app = App.getApp();
    }

    @BeforeAll
    public static void beforeAll() throws IOException {
        mockWebServer = new MockWebServer();
        MockResponse response = new MockResponse()
                .setBody(Files.readString(Paths.get("./src/test/resources/index.html")));
        mockWebServer.enqueue(response);
    }

    @AfterAll
    public static void afterAll() throws IOException {
        mockWebServer.shutdown();
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
        JavalinTest.test(app, (server, client) -> {
            String requestBody = "url=https://vk.com";
            Response response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertEquals(200, response.code());
            assertTrue(response.body().string().contains("https://vk.com"));
        });
    }

    @Test
    public void testPostUrlFailed() {
        JavalinTest.test(app, (server, client) -> {
            String requestBody = "url=https://vk.com";
            Response response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertEquals(200, response.code());
            assertTrue(response.body().string().contains("Анализатор страниц"));
        });
    }

    @Test
    public void testShowUrl() throws SQLException {
        Url url = new Url("https://vk.com");
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            Response response = client.get(NamedRoutes.urlPath(url.getId()));
            assertEquals(200, response.code());
        });
    }

    @Test
    public void testNotFound() {
        JavalinTest.test(app, (server, client) -> {
            Response response = client.get(NamedRoutes.urlPath(99999999L));
            assertEquals(404, response.code());
        });
    }

    @Test public void testDuplicateUrl() throws SQLException {
        Url url = new Url("https://vk.com");
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            String requestBody = "url=https://vk.com";
            Response response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertEquals(200, response.code());
            assertTrue(response.body().string().contains("Анализатор страниц"));
        });
    }

    @Test
    public void testCheckUrl() throws SQLException {
        String name = mockWebServer.url("/").toString();
        Url url = new Url(name);
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            client.post(NamedRoutes.urlCheckPath(1L)); // делаем post запрос, сохраняя в БД наш первый чек
            UrlCheckRepository.setUrlChecks(url); // вытаскиваем все чеки для нашего урла
            UrlCheck urlCheck = url.getUrlCheckList().get(0); // достаем первый чек
            assertEquals(200, urlCheck.getStatusCode());
            assertEquals("Simple title", urlCheck.getTitle());
            assertEquals("Simple header", urlCheck.getH1());
            assertEquals("Simple description", urlCheck.getDescription());
        });
    }
}
