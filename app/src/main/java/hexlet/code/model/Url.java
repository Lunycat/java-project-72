package hexlet.code.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Getter
@NoArgsConstructor
public class Url {

    @Setter
    private Long id;
    private String name;
    private Timestamp createdAt;

    public Url(String name) {
        this.name = name;
    }

    public Url(Long id, String name, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(createdAt);
    }
}
