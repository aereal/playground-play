package deary.models

import scala.collection.immutable.Seq

import deary.repository.{ArticleComponent => ArticleRepositoryComponent}

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
