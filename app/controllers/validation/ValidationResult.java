package controllers.validation;

import java.util.*;

/**
 * Validation result wrapper.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class ValidationResult {

    private final Map<String, List<String>> messages = new HashMap<>();

    public void addMessage(String key, String msg) {
        if (!this.messages.containsKey(key)) {
            this.messages.put(key, new ArrayList<>());
        }
        this.messages.get(key).add(msg);
    }

    public boolean success() {
        return messages.isEmpty();
    }

    public Map<String, List<String>> getMessages() {
        return messages;
    }
}
