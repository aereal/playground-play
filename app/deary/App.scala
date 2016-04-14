package deary

import slick.driver.JdbcProfile
import slick.backend.DatabaseConfig

import deary.service.ArticleServiceComponent
import deary.infra.db.DBComponent
import deary.repository.{ArticleComponent => ArticleRepositoryComponent}

trait App extends ArticleServiceComponent with ArticleRepositoryComponent with DBComponent[JdbcProfile] {
  val articleRepository = new ArticleRepository
  val articleService = new ArticleService
  val dbConfig = DatabaseConfig.forConfig[JdbcProfile]("mysql-local")
}
