package deary.infra.db

import slick.backend.DatabaseConfig
import slick.profile.BasicProfile

trait DBComponent[P <: BasicProfile] {
  val dbConfig: DatabaseConfig[P]

  def db = dbConfig.db
}
