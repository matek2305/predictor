package utils;

import akka.actor.ActorRef;
import play.libs.Akka;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public final class AkkaUtils {

    public static void scheduleActor(ActorRef actor, FiniteDuration interval, Object message) {
        scheduleActor(actor, Duration.create(0, TimeUnit.MILLISECONDS), interval, message);
    }

    public static void scheduleActor(ActorRef actor, FiniteDuration initDelay, FiniteDuration interval, Object message) {
        Akka.system().scheduler().schedule(initDelay, interval, actor, message, Akka.system().dispatcher(), null);
    }

    private AkkaUtils() {

    }
}
