package deary.repository

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import slick.jdbc.GetResult

import deary.infra.db.{MixinDB, UsesDB}
import deary.models.Article

import scala.collection.immutable.Seq

trait UsesArticleRepository {
  val articleRepository: ArticleRepository
}

trait ArticleRepository extends UsesDB {
  def all(): Seq[Article]
  def save(article: Article): Int
}

object ArticleRepository extends ArticleRepository with MixinDB {
  implicit val GetArticleResult = GetResult(r => Article(r.<<, r.<<, r.<<))

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

trait MixinArticleRepository {
  val articleRepository = ArticleRepository
}
