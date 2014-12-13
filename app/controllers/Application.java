package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * The main set of web services.
 */
public class Application extends Controller {

    public static Result index() {
        return ok(views.html.index.render("Predictor"));
    }
}
