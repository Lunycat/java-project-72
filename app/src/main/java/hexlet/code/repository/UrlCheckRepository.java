package hexlet.code.repository;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.ResultSet;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class UrlCheckRepository extends BaseRepository {

    public static void save(UrlCheck urlCheck) throws SQLException {
        String sql = """
                INSERT INTO url_checks (url_id, status_code, title, h1, description, created_at)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, urlCheck.getUrl().getId());
            stmt.setInt(2, urlCheck.getStatusCode());
            stmt.setString(3, urlCheck.getTitle());
            stmt.setString(4, urlCheck.getH1());
            stmt.setString(5, urlCheck.getDescription());
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
            ResultSet pointerKey = stmt.getGeneratedKeys();

            if (pointerKey.next()) {
                urlCheck.setId(pointerKey.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static void setUrlChecks(Url url) throws SQLException {
        String sql = """
                SELECT * FROM url_checks
                WHERE url_id = ?
                ORDER BY created_at DESC
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, url.getId());
            ResultSet pointer = stmt.executeQuery();

            while (pointer.next()) {
                Long id = pointer.getLong("id");
                int statusCode = pointer.getInt("status_code");
                String title = pointer.getString("title");
                String h1 = pointer.getString("h1");
                String description = pointer.getString("description");
                Timestamp createdAt = pointer.getTimestamp("created_at");

                UrlCheck urlCheck = UrlCheck.builder()
                        .id(id)
                        .statusCode(statusCode)
                        .title(title)
                        .h1(h1)
                        .description(description)
                        .createdAt(createdAt)
                        .build();

                url.getUrlCheckList().add(urlCheck);
            }
        }
    }

    public static Map<Long, UrlCheck> findLastChecks() throws SQLException {
        String sql = """
                SELECT DISTINCT ON (url_id) * FROM url_checks
                ORDER BY url_id DESC, id DESC
                """;
        Map<Long, UrlCheck> result = new HashMap<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet pointer = stmt.executeQuery();

            while (pointer.next()) {
                Long urlId = pointer.getLong("url_id");
                int statusCode = pointer.getInt("status_code");
                Timestamp createdAt = pointer.getTimestamp("created_at");

                UrlCheck urlCheck = UrlCheck.builder()
                        .statusCode(statusCode)
                        .createdAt(createdAt)
                        .build();

                result.put(urlId, urlCheck);
            }
        }

        return result;
    }
}
