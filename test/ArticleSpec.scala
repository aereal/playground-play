import org.scalatest.FunSuite

import models.{Article, ArticleRepository}

class ArticleSpec extends FunSuite {
  test("Article is a ...") {
    val article = Article(title = "title", body = "body")
    assert(article.title == "title")
  }

  test("ArticleRepository") {
    val article = Article(title = "title", body = "body")
    val articleId = ArticleRepository.save(article)
    val resolved: Article = ArticleRepository.resolveById(articleId)
    assert(article == resolved)
  }
}
