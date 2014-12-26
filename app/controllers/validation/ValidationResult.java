package controllers.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hatake on 2014-12-26.
 */
public class ValidationResult {

    private final List<String> messages = new ArrayList<>();

    public void addMessage(String msg) {
        messages.add(msg);
    }

    public List<String> getMessages() {
        return messages;
    }
}
