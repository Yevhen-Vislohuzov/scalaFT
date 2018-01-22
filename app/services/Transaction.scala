package services

import mapper.TransactionTypeMapper
import play.api.data.{Form, Forms}
import play.api.data.Forms.{bigDecimal, mapping, text}
import play.api.libs.functional.syntax._
import play.api.libs.json.{Json, Reads, Writes, __}
import services.TransactionType.TransactionType

case class Transaction(id: String,
                       amount: BigDecimal,
                       transactionType: TransactionType
                       )

object Transaction {
  val form = Form(mapping(
    "id" -> text,
    "amount" -> bigDecimal,
    "transactionType" -> TransactionTypeMapper.transactionType
  )(Transaction.apply)(Transaction.unapply))

  implicit val readsTransaction: Reads[Transaction] = (
    (__ \ "id").read[String] and
    (__ \ "amount").read[BigDecimal] and
    (__ \ "transactionType").read[TransactionType]
    ) (Transaction.apply _)

  implicit val writesItem: Writes[Transaction] = Writes[Transaction] {
    case Transaction(id, amount, transactionType) =>
      Json.obj(
        "id" -> id,
        "amount" -> amount,
        "transactionType" -> transactionType
      )
  }
}
