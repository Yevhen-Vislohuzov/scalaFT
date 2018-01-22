package services

import play.api.libs.json.{Format, JsString, JsSuccess, JsValue}

object TransactionType extends Enumeration {
  type TransactionType = Value
  val DEBIT, CREDIT = Value

  implicit val enumFormat = new Format[TransactionType] {
    def reads(json: JsValue) = JsSuccess(TransactionType.withName(json.as[String]))

    def writes(enum: TransactionType) = JsString(enum.toString)
  }
}