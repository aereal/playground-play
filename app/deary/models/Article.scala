package deary.models

import scala.collection.immutable.Seq

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import slick.profile.BasicProfile
import slick.jdbc.GetResult

case class Article(
  id: Int,
  title: String,
  body: String
)

trait ArticleServiceComponent {
  self: ArticleRepositoryComponent =>

  val articleService: ArticleService

  class ArticleService {
    def write(title: String, body: String): Unit = {
      val id = 1
      articleRepository.save(Article(id, title, body))
    }

    def findAll: Seq[Article] =
      articleRepository.all
  }
}

trait ArticleRepositoryComponent {
  self: DBComponent[JdbcProfile] =>

  val articleRepository: ArticleRepository

  implicit val GetArticleResult = GetResult(r => Article(r.<<, r.<<, r.<<))

  class ArticleRepository {
    import scala.concurrent.Await
    import scala.concurrent.duration.Duration

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

trait DBComponent[P <: BasicProfile] {
  val dbConfig: DatabaseConfig[P]

  def db = dbConfig.db
}
