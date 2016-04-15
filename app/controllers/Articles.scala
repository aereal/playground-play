package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json

import deary.dto.Article.Json._
import deary.service.ArticleServiceComponent

trait ArticlesComponent {
  self: ArticleServiceComponent =>

  val articleService: ArticleService

  class ArticlesController extends Controller {
    def index() = Action { implicit request =>
      val articles = articleService.findAll
      render {
        case Accepts.Html() => Ok(views.html.articles( articles ))
        case Accepts.Json() => Ok(Json.toJson( articles ))
      }
    }
  }
}
