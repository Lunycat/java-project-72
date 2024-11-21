package hexlet.code.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class BasePage {
    private Map<String, String> flash;

    public BasePage() {
        flash = new HashMap<>();
    }
}
