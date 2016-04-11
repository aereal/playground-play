package models

case class Article(
  title: String,
  body: String
)

object ArticleRepository {
  var lastId: Int = 1
  var repo: List[Article] = List()

  def save(article: Article): Int = {
    val insertedId = lastId
    lastId = lastId + 1
    repo = repo ::: List(article)
    insertedId
  }

  def resolveById(id: Int): Article = {
    repo(id - 1)
  }
}
