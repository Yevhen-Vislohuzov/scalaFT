package controllers

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._
import services.{Transaction, TransactionManager}

@Singleton
class TransactionController @Inject()(cc: ControllerComponents,
                                      trsManager: TransactionManager) extends AbstractController(cc) {

  /**
    * Create an action that responds with the current transactions.
    * The result is JSON. This `Action` is mapped to
    * `GET /transaction` requests by an entry in the `routes` config file.
    */
  def get = Action {
    val trs = trsManager.getTransactions
    Ok(Json.toJson(trs))
  }

  /**
    * Create an action that receive transaction and save to DB.
    * The result is JSON representation of inserted trs. This `Action` is mapped to
    * `POST /transaction` requests by an entry in the `routes` config file.
    */
  def add = Action { implicit request =>
    val trs = Transaction.form.bindFromRequest().get
    trsManager.addTransaction(trs)
    Ok(Json.toJson(trs))
  }

}
