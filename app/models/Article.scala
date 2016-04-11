package models

case class Article(
  title: String,
  body: String
)

object ArticleRepository {
  var repo: List[Article] = List()

  def save(article: Article) = {
    repo = repo ::: List(article)
  }
}
