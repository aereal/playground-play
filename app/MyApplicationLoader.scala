import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.ApplicationLoader.Context
import play.api.routing._
import play.api.routing.sird._

class MyApplicationLoader extends ApplicationLoader {
  def load(context: Context) = new BuiltInComponentsFromContext(context) {
    val router = Router.from {
      case GET(p"/") => Action {
        Ok("<h1>Play</h1>").as("text/html; charset=utf-8")
      }
    }
  }.application
}
