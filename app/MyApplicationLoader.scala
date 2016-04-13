import play.api.ApplicationLoader.Context
import play.api._
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.DatabaseConfig
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc.Results._
import play.api.mvc._
import play.api.routing._
import play.api.routing.sird._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import slick.jdbc.GetResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration.Duration

import models.Article

class MyApplicationLoader extends ApplicationLoader {
  implicit val articleWrites: Writes[Article] = (
    (JsPath \ "id").write[Int] and
    (JsPath \ "title").write[String] and
    (JsPath \ "body").write[String]
  )(unlift(Article.unapply))

  implicit val articleReads: Reads[Article] = (
    (JsPath \ "id").read[Int] and
    (JsPath \ "title").read[String] and
    (JsPath \ "body").read[String]
  )(Article.apply _)

  implicit val GetArticleResult = GetResult(r => Article(r.<<, r.<<, r.<<))

  val db = Database.forConfig("mysql-local")

  def load(context: Context) = new BuiltInComponentsFromContext(context) {
    val router = Router.from {
      case GET(p"/articles") => Action.async { implicit request =>
        db.run(
          sql"""SELECT * FROM article""".as[Article]
        ).map(articles => Json.toJson(articles)).map(json => Ok(json))
      }

      case POST(p"/articles.json") => Action(BodyParsers.parse.json) { implicit request =>
        request.body.validate[Article].fold(
          errors => {
             BadRequest(Json.obj("ok" -> false, "errors" -> JsError.toJson(errors)))
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
            Ok(Json.obj("ok" -> true))
          }
        )
      }
    }
  }.application
}
