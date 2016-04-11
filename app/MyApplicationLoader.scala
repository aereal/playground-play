import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.ApplicationLoader.Context
import play.api.routing._
import play.api.routing.sird._

import models.{Article, ArticleRepository}

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

  def load(context: Context) = new BuiltInComponentsFromContext(context) {
    val router = Router.from {
      case GET(p"/articles.json") => Action {
        val json = Json.toJson(ArticleRepository.repo)
        Ok(json)
      }

      case POST(p"/articles.json") => Action(BodyParsers.parse.json) { implicit request =>
        request.body.validate[Article].fold(
          errors => {
             BadRequest(Json.obj("ok" -> false, "errors" -> JsError.toJson(errors)))
          },
          article => {
            ArticleRepository.save(article)
            Ok(Json.obj("ok" -> true))
          }
        )
      }
    }
  }.application
}
