package utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Date time utils.
 *
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public final class DateHelper {

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDateTime tommorow() {
        return LocalDateTime.now().plusDays(1);
    }

    public static LocalDateTime tommorowAt(int hour, int minute) {
        return tommorowAt(hour, minute, 0);
    }

    public static LocalDateTime tommorowAt(int hour, int minute, int second) {
        return tommorowAt(hour, minute, second, 0);
    }

    public static LocalDateTime tommorowAt(int hour, int minute, int second, int nano) {
        return tommorow().withHour(hour).withMinute(minute).withSecond(second).withNano(nano);
    }

    public static LocalDateTime todayAt(int hour, int minute) {
        return todayAt(hour, minute, 0);
    }

    public static LocalDateTime todayAt(int hour, int minute, int second) {
        return todayAt(hour, minute, second, 0);
    }

    public static LocalDateTime todayAt(int hour, int minute, int second, int nano) {
        return LocalDateTime.now().withHour(hour).withMinute(minute).withSecond(second).withNano(nano);
    }

    private DateHelper() {

    }
}