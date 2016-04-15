import play.api.{ApplicationLoader, BuiltInComponentsFromContext}
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, BodyParsers, Results}
import play.api.routing.Router
import play.api.routing.sird._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import slick.driver.MySQLDriver.api._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

import deary.dto.Article.Json._
import deary.models.Article

import controllers.Controllers

class MyApplicationLoader extends ApplicationLoader with Controllers {
  def load(context: ApplicationLoader.Context) = new BuiltInComponentsFromContext(context) {
    val router = Router.from {
      case GET(p"/articles") => { articlesController.index }

      case POST(p"/articles") => Action(BodyParsers.parse.json) { implicit request =>
        request.body.validate[Article].fold(
          errors => {
             Results.BadRequest(Json.obj("ok" -> false, "errors" -> JsError.toJson(errors)))
          },
          article => {
            articleService.write(article.title, article.body)
            Results.Ok(Json.obj("ok" -> true))
          }
        )
      }
    }
  }.application
}
