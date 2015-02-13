package controllers.validation;

import java.util.*;

/**
 * Validation result wrapper.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class ValidationResult {

    private final List<String> messages = new LinkedList<>();

    public void addMessage( String msg) {
        this.messages.add(msg);
    }

    public boolean success() {
        return messages.isEmpty();
    }

    public List<String> getMessages() {
        return messages;
    }
}
