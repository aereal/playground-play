package deary

import slick.driver.JdbcProfile
import slick.backend.DatabaseConfig

import deary.models.{ArticleServiceComponent, ArticleRepositoryComponent}
import deary.infra.db.DBComponent

trait App extends ArticleServiceComponent with ArticleRepositoryComponent with DBComponent[JdbcProfile] {
  val articleRepository = new ArticleRepository
  val articleService = new ArticleService
  val dbConfig = DatabaseConfig.forConfig[JdbcProfile]("mysql-local")
}
