package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.Utils;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class App {

    public static void main(String[] args) throws IOException, SQLException {
        Javalin app = getApp();

        app.start(Utils.getPort());
    }

    public static Javalin getApp() throws IOException, SQLException {
        HikariConfig configHi = new HikariConfig();
        configHi.setJdbcUrl(Utils.getUrl());

        HikariDataSource dataSource = new HikariDataSource(configHi);
        String sql = Utils.readResources("schema.sql");

        log.info(sql);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }

        BaseRepository.dataSource = dataSource;

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });

        app.get("/", ctx -> {
            ctx.result("Hello World");
        });

        return app;
    }
}
