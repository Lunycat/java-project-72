package hexlet.code.util;

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
}
