package controllers.validation;

import java.util.*;

/**
 * Created by Hatake on 2014-12-26.
 */
public class ValidationResult {

    private final Map<String, List<String>> messages = new HashMap<>();

    public void addMessage(String key, String msg) {
        if (!this.messages.containsKey(key)) {
            this.messages.put(key, new ArrayList<>());
        }
        this.messages.get(key).add(msg);
    }

    public Map<String, List<String>> getMessages() {
        return messages;
    }
}
