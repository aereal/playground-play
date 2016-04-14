package deary.service

import scala.collection.immutable.Seq

import deary.repository.{ArticleComponent => ArticleRepositoryComponent}
import deary.models.Article

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
