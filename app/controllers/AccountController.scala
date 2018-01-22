package controllers

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.{Account, AccountStorageImpl, Transaction}

@Singleton
class AccountController @Inject()(cc: ControllerComponents,
                                  accStorage: AccountStorageImpl) extends AbstractController(cc) {
  /**
    * Create an action that receive and stores account.
    * The result is JSON representation of inserted account.
    */
  def add = Action { implicit request =>
    val account = Account.form.bindFromRequest().get
    val result = accStorage.addAccount(account)
    if (result) Ok(Json.toJson(account)) else BadRequest
  }

  /**
    * Create an action that returns account by name.
    * The result is JSON representation of found account.
    */
  def get(accountName: String) = Action {
    val account: Option[Account] = accStorage.getAccount(accountName)
    if (account.isEmpty) NotFound else Ok(Json.toJson(account))
  }

  /**
    * Create an action that responds with the current transactions for specified account.
    * The result is JSON representation of found transactions.
    */
  def getTransactions(accountName: String) = Action {
    try {
      val trs = accStorage.getTransactions(accountName)
      Ok(Json.toJson(trs))
    }
    catch {
      case e: IllegalArgumentException => NotFound
    }
  }

  /**
    * Create an action that receive transaction and apply to account.
    * The result is JSON representation of inserted trs.
    */
  def addTransaction(accountName: String) = Action { implicit request =>
    val trs = Transaction.form.bindFromRequest().get
    val result = accStorage.addTransaction(accountName, trs)
    if (result) Ok(Json.toJson(trs)) else BadRequest
  }

}
