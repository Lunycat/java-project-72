package hexlet.code.util;

import hexlet.code.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;

import java.util.stream.Collectors;

public class Utils {

    public static int getPort() {
        String port = System
                .getenv()
                .getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    public static String getUrl() {
        return System
                .getenv()
                .getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:hexlet_project");
    }

    public static String readResources(String file) throws IOException {
        InputStream is = App.class.getClassLoader().getResourceAsStream(file);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
