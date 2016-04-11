import org.scalatest.FunSuite

import models.{Article, ArticleRepository}

class ArticleSpec extends FunSuite {
  test("Article is a ...") {
    val article = Article(id = 1, title = "title", body = "body")
    assert(article.title == "title")
  }

  test("ArticleRepository") {
    val article = Article(id = 1, title = "title", body = "body")
    ArticleRepository.save(article)
    val resolved: Article = ArticleRepository.resolveById(article.id)
    assert(article == resolved)
  }
}
