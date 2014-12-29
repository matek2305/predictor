package utils;

import controllers.validation.ValidationResult;
import play.libs.F;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.SimpleResult;

/**
 * Bad request action util.
 *
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public class BadRequestAction extends Action.Simple {

    public static final String PREDICTOR_STATUS_REASON_HEADER = "Predictor-Status-Reason";

    private static final String VALIDATION_FAILED = "Validation failed";

    public static BadRequestAction fromValidationResult(ValidationResult result) {
        SimpleResult simpleResult = badRequest(Json.toJson(result));
        return new BadRequestAction(simpleResult, VALIDATION_FAILED);
    }

    public static BadRequestAction fromAuthenticationStatus(PredictorSecurity.Status status) {
        return new BadRequestAction(badRequest(), status.getStatusReasonHeaderValue());
    }

    private SimpleResult simpleResult;
    private String predictorStatusReasonHeaderValue;

    private BadRequestAction(SimpleResult simpleResult, String predictorStatusReasonHeaderValue) {
        this.simpleResult = simpleResult;
        this.predictorStatusReasonHeaderValue = predictorStatusReasonHeaderValue;
    }

    @Override
    public F.Promise<SimpleResult> call(Http.Context context) throws Throwable {
        context.response().setHeader(PREDICTOR_STATUS_REASON_HEADER, predictorStatusReasonHeaderValue);
        return F.Promise.pure(simpleResult);
    }
}
