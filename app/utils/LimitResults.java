package utils;

import org.springframework.data.domain.PageRequest;

/**
 * Created by Hatake on 2015-02-15.
 */
public class LimitResults extends PageRequest {

    public LimitResults(int limit) {
        super(0, limit);
    }
}
