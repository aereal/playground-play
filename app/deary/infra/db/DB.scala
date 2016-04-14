package deary.infra.db

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import slick.profile.BasicProfile

trait UsesDB {
  def dbConfig: DatabaseConfig[JdbcProfile]
  def db: JdbcProfile#Backend#Database
}

trait MixinDB extends UsesDB {
  def dbConfig = DatabaseConfig.forConfig[JdbcProfile]("mysql-local")
  def db = dbConfig.db
}
