package deary.repository

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import slick.jdbc.GetResult

import deary.infra.db.DBComponent
import deary.models.Article

trait ArticleComponent {
  self: DBComponent[JdbcProfile] =>

  val articleRepository: ArticleRepository

  implicit val GetArticleResult = GetResult(r => Article(r.<<, r.<<, r.<<))

  class ArticleRepository {
    def all = {
      Await.result(
        db.run(
          sql"""SELECT * FROM article""".as[Article]
        ),
        Duration.Inf
      )
    }

    def save(article: Article) = {
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
    }
  }
}
