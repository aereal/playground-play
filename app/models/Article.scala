package models

import scala.collection.immutable.Seq

case class Article(
  id: Int,
  title: String,
  body: String
)

object ArticleRepository extends Seq[Article] {
  var lastId: Int = 1
  private var repo: List[Article] = List()

  def apply(idx: Int) = repo(idx)

  def length = repo.length

  def iterator: Iterator[Article] = repo.toIterator

  def all: List[Article] = repo

  def save(article: Article): Unit = {
    lastId = article.id
    repo = repo ::: List(article)
  }

  def resolveById(id: Int): Article = {
    repo(id - 1)
  }
}
