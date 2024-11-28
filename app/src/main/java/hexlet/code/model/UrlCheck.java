package hexlet.code.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Getter
@Setter
@Builder
public class UrlCheck {

    private Long id;
    private Long urlId;
    private int statusCode;
    private String title;
    private String h1;
    private String description;
    private Timestamp createdAt;
    private Url url;

    public String getCreatedAt() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(createdAt);
    }
}
