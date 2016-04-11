package models

case class Article(
  id: Int,
  title: String,
  body: String
)

object ArticleRepository {
  var lastId: Int = 1
  private var repo: List[Article] = List()

  def save(article: Article): Unit = {
    lastId = article.id
    repo = repo ::: List(article)
  }

  def resolveById(id: Int): Article = {
    repo(id - 1)
  }
}
