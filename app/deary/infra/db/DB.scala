package deary.infra.db

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import slick.profile.BasicProfile

trait DBComponent[P <: BasicProfile] {
  val dbConfig: DatabaseConfig[P]

  def db = dbConfig.db
}

trait UsesDB {
  def dbConfig: DatabaseConfig[JdbcProfile]
  def db: JdbcProfile#Backend#Database
}

trait MixinDB extends UsesDB {
  def dbConfig = DatabaseConfig.forConfig[JdbcProfile]("mysql-local")
  def db = dbConfig.db
}
