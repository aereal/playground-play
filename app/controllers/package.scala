package object controllers {
  import deary.App

  import controllers.ArticlesComponent

  trait Controllers extends App with ArticlesComponent {
    val articlesController = new ArticlesController
  }
}
