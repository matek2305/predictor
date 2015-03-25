package utils;

import org.apache.commons.math3.random.RandomDataGenerator;

/**
 * Created by Hatake on 2015-03-24.
 */
public final class RandomIdGenerator {

    private static final RandomDataGenerator RANDOM_DATA_GENERATOR = new RandomDataGenerator();

    public static Long generate() {
        return RANDOM_DATA_GENERATOR.nextLong(1, Long.MAX_VALUE);
    }

    private RandomIdGenerator() {

    }
}
