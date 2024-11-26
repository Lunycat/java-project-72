package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Url {

    @Setter
    private Long id;
    private String name;
    private Timestamp createdAt;
    private List<UrlCheck> urlCheckList;

    public Url() {
        urlCheckList = new ArrayList<>();
    }

    public Url(String name) {
        this.name = name;
        urlCheckList = new ArrayList<>();
    }

    public Url(Long id, String name, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        urlCheckList = new ArrayList<>();
    }

    public String getCreatedAt() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(createdAt);
    }
}
