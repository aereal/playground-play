import org.scalatest.FunSuite

import models.Article

class ArticleSpec extends FunSuite {
  test("Article is a ...") {
    val article = Article(title = "title", body = "body")
    assert(article.title == "title")
  }
}
