import play.api.{ApplicationLoader, BuiltInComponentsFromContext}
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.DatabaseConfig
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, BodyParsers, Results}
import play.api.routing.Router
import play.api.routing.sird._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import slick.jdbc.GetResult

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import models.Article
import deary.dto.Article.Json._

class MyApplicationLoader extends ApplicationLoader {
  implicit val GetArticleResult = GetResult(r => Article(r.<<, r.<<, r.<<))

  val db = Database.forConfig("mysql-local")

  def load(context: ApplicationLoader.Context) = new BuiltInComponentsFromContext(context) {
    val router = Router.from {
      case GET(p"/articles") => Action.async { implicit request =>
        db.run(
          sql"""SELECT * FROM article""".as[Article]
        ).map(articles => Json.toJson(articles)).map(json => Results.Ok(json))
      }

      case POST(p"/articles.json") => Action(BodyParsers.parse.json) { implicit request =>
        request.body.validate[Article].fold(
          errors => {
             Results.BadRequest(Json.obj("ok" -> false, "errors" -> JsError.toJson(errors)))
          },
          article => {
            Await.result(
              db.run(
                sqlu"""
                  INSERT article
                  SET
                    title = ${article.title},
                    body = ${article.body}
                """
              ),
              Duration.Inf
            )
            Results.Ok(Json.obj("ok" -> true))
          }
        )
      }
    }
  }.application
}
