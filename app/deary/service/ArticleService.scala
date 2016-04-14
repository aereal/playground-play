package deary.service

import scala.collection.immutable.Seq

import deary.repository.{MixinArticleRepository, UsesArticleRepository}
import deary.models.Article

trait ArticleService extends UsesArticleRepository {
  def write(title: String, body: String): Unit
  def findAll: Seq[Article]
}

trait UsesArticleService {
  val articleService: ArticleService
}

object ArticleService extends ArticleService with MixinArticleRepository {
  def write(title: String, body: String): Unit = {
    val id = 1
    articleRepository.save(Article(id, title, body))
  }

  def findAll: Seq[Article] =
    articleRepository.all
}

trait MixinArticleService {
  val articleService = ArticleService
}
