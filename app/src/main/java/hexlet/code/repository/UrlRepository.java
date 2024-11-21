package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, url.getCreatedAt());
            stmt.executeUpdate();
            ResultSet key = stmt.getGeneratedKeys();

            if (key.next()) {
                url.setId(key.getLong(1));
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
                Timestamp timestamp = pointer.getTimestamp("created_at");

                Url url = new Url(name, timestamp);
                url.setId(id);

                urls.add(url);
            }

            return urls;
        }
    }
}
