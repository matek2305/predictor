package utils;

import domain.entity.Predictor;
import domain.repository.PredictorRepository;
import org.apache.commons.lang3.RandomStringUtils;
import play.mvc.Http;
import utils.settings.PredictorSettings;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * Predictor security util class.
 *
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class PredictorSecurity {

    public static final String AUTH_TOKEN_HEADER = "Predictor-Authentication-Token";

    private static final int AUTH_TOKEN_LENGTH = 32;

    private PredictorRepository predictorRepository;

    public PredictorSecurity(PredictorRepository predictorRepository) {
        this.predictorRepository = predictorRepository;
    }

    public Status authenticateRequest(Http.Request request) {
        if (!request.headers().containsKey(AUTH_TOKEN_HEADER)) {
            return Status.NO_AUTH_TOKEN;
        }

        String token = request.getHeader(AUTH_TOKEN_HEADER);
        Optional<Predictor> predictor = predictorRepository.findByAuthenticationToken(token);
        if (!predictor.isPresent() || !token.equals(predictor.get().authenticationToken)) {
            return Status.INVALID_TOKEN;
        }

        long expirationTimeInMillis = predictor.get().tokenExpirationDate.getTime();
        if (new Date().getTime() > expirationTimeInMillis) {
            return Status.TOKEN_EXPIRED;
        }

        predictor.get().tokenExpirationDate = calculateTokenExpirationDate();
        predictorRepository.save(predictor.get());

        request.setUsername(predictor.get().login);
        return Status.SUCCESS;
    }

    public static String generateToken() {
        return RandomStringUtils.randomAlphanumeric(AUTH_TOKEN_LENGTH);
    }

    public static Date calculateTokenExpirationDate() {
        int expirationTime = PredictorSettings.getInt(PredictorSettings.Setting.AUTH_TOKEN_EXPIRATION_DATE);
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(expirationTime);
        return DateHelper.toDate(expirationDate);
    }

    public static String generateCompetitionCode() {
        final int length = PredictorSettings.getInt(PredictorSettings.Setting.COMPETITION_SECURITY_CODE_LENGTH);
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public enum Status {
        SUCCESS(null),
        INVALID_TOKEN("Authentication token is invalid"),
        TOKEN_EXPIRED("Authentication token expired"),
        NO_AUTH_TOKEN("No authentication token"),
        FAILED("Authentication failed");


        private final String statusReasonHeaderValue;

        Status(final String statusReasonHeaderValue) {
            this.statusReasonHeaderValue = statusReasonHeaderValue;
        }

        public String getStatusReasonHeaderValue() {
            return statusReasonHeaderValue;
        }
    }
}
