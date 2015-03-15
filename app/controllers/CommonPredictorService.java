package controllers;

import domain.entity.Predictor;
import domain.repository.PredictorRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Results;

import javax.inject.Inject;
import java.util.EnumSet;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Base predictor services controller.
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public abstract class CommonPredictorService extends Controller {

    private static final String LIST_SEPARATOR = ",";

    @Inject
    private PredictorRepository predictorRepository;

    protected Predictor getCurrentUser() {
        if (isBlank(request().username())) {
            throw new IllegalStateException("getCurrentUser() called with no username in request!");
        }
        
        return predictorRepository.findByLogin(request().username()).get();
    }

    protected PageRequest getPageRequest(Sort sort) {
        int page = getIntFromQueryString("page").orElse(0);
        int size = getIntFromQueryString("size").orElse(0);
        return new PageRequest(page, size, sort);
    }

    protected Optional<Integer> getIntFromQueryString(String param) {
        if (!request().queryString().containsKey(param)) {
            return Optional.empty();
        }

        try {
            return Optional.of(Integer.parseInt(request().getQueryString(param)));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    protected Long getLongFromQueryString(String param) {
        if (!request().queryString().containsKey(param)) {
            return null;
        }

        try {
            return Long.parseLong(request().getQueryString(param));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    protected boolean getBoolFromQueryString(String param) {
        return new Boolean(request().getQueryString(param)).booleanValue();
    }

    protected <T extends Enum<T>> EnumSet<T> getEnumSetFromQueryString(String param, Class<T> type) {
        if (!request().queryString().containsKey(param)) {
            return EnumSet.noneOf(type);
        }

        String value = request().getQueryString(param);
        if (StringUtils.isBlank(value)) {
            return EnumSet.noneOf(type);
        }

        EnumSet<T> result = EnumSet.noneOf(type);
        for (String elem : value.split(LIST_SEPARATOR)) {
            result.add(parseEnumValue(elem.trim(), type));
        }

        return result;
    }

    private <T extends Enum<T>> T parseEnumValue(String value, Class<T> type) {
        for (T e : EnumSet.allOf(type)) {
            if (e.name().equals(value)) {
                return e;
            }
        }

        throw new IllegalArgumentException(value + " is not valid value for " + type.getSimpleName() + " enum");
    }

    protected <T> T prepareRequest(Class<T> type) {
        return Json.fromJson(request().body().asJson(), type);
    }

    protected PredictorRepository getPredictorRepository() {
        return predictorRepository;
    }

    public static <T> Results.Status ok(T data) {
        return ok(Json.toJson(data));
    }

    public static <T> Results.Status created(T data) {
        return created(Json.toJson(data));
    }
}
