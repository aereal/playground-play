package deary

import slick.driver.JdbcProfile
import slick.backend.DatabaseConfig

import deary.service.MixinArticleService
import deary.infra.db.MixinDB
import deary.repository.MixinArticleRepository

trait App extends MixinArticleService with MixinArticleRepository with MixinDB {
}
