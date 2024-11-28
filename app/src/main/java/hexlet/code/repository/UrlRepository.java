package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.Timestamp;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        String sql = """
                INSERT INTO urls (name, created_at)
                VALUES (?, ?)
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
            ResultSet pointerKey = stmt.getGeneratedKeys();

            if (pointerKey.next()) {
                url.setId(pointerKey.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static List<Url> getEntities() throws SQLException {
        String sql = "SELECT * FROM urls";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet pointer = stmt.executeQuery();
            List<Url> urls = new ArrayList<>();

            while (pointer.next()) {
                Long id = pointer.getLong("id");
                String name = pointer.getString("name");
                Timestamp createdAt = pointer.getTimestamp("created_at");

                Url url = new Url(id, name, createdAt);

                urls.add(url);
            }

            return urls;
        }
    }

    public static Optional<Url> findName(String nameSearch) throws SQLException {
        String sql = """
            SELECT * FROM urls
            WHERE name = ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nameSearch);
            ResultSet pointer = stmt.executeQuery();

            if (pointer.next()) {
                Long id = pointer.getLong("id");
                String name = pointer.getString("name");
                Timestamp createdAt = pointer.getTimestamp("created_at");

                Url url = new Url(id, name, createdAt);

                return Optional.of(url);
            }

            return Optional.empty();
        }
    }

    public static Optional<Url> find(Long idSearch) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idSearch);
            ResultSet pointer = stmt.executeQuery();

            if (pointer.next()) {
                Long id = pointer.getLong("id");
                String name = pointer.getString("name");
                Timestamp createdAt = pointer.getTimestamp("created_at");

                Url url = new Url(id, name, createdAt);

                return Optional.of(url);
            }

            return Optional.empty();
        }
    }
}
