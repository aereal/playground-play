package deary.dto

import deary.models.{Article => ArticleEntity}

object Article {
  object Json {
    import play.api.libs.functional.syntax._
    import play.api.libs.json.{Reads, Writes, JsPath}

    implicit val articleWrites: Writes[ArticleEntity] = (
      (JsPath \ "id").write[Int] and
      (JsPath \ "title").write[String] and
      (JsPath \ "body").write[String]
    )(unlift(ArticleEntity.unapply))

    implicit val articleReads: Reads[ArticleEntity] = (
      (JsPath \ "id").read[Int] and
      (JsPath \ "title").read[String] and
      (JsPath \ "body").read[String]
    )(ArticleEntity.apply _)
  }
}
