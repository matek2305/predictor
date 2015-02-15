package filters

import play.api.mvc.{EssentialAction, EssentialFilter, RequestHeader}

import scala.concurrent.ExecutionContext.Implicits.global;

/**
 * Cross-Origin-Resource-Share headers filter.
 *
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
class CorsFilter extends EssentialFilter {

  override def apply(next: EssentialAction) = new EssentialAction {

    override def apply(requestHeader: RequestHeader) = {
      next(requestHeader).map { result =>
        result.withHeaders(
          "Access-Control-Allow-Origin" -> "*",
          "Access-Control-Expose-Headers" -> "Predictor-Status-Reason"
        )
      }
    }
  }
}
