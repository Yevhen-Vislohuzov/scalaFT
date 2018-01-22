package services

import play.api.data.Form
import play.api.data.Forms.{bigDecimal, list, mapping, text}
import play.api.libs.functional.syntax._
import play.api.libs.json.{Json, Reads, Writes, __}

case class Account(name: String,
                   var amount: BigDecimal,
                   var transactions: List[Transaction]
                  )

object Account {

  val form = Form(mapping(
    "name" -> text,
    "amount" -> bigDecimal,
    "transactions" -> list(Transaction.form.mapping),
  )(Account.apply)(Account.unapply))

  implicit val readsAccount: Reads[Account] = (
    (__ \ "name").read[String] and
      (__ \ "amount").read[BigDecimal] and
      (__ \ "transactions").read[List[Transaction]]
    ) (Account.apply _)

  implicit val writesItem: Writes[Account] = Writes[Account] {
    case Account(name, amount, transactions) =>
      Json.obj(
        "name" -> name,
        "amount" -> amount,
        "transactions" -> transactions
      )
  }
}


