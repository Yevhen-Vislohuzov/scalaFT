package mapper

import play.api.data.format.Formatter
import play.api.data.{FormError, Forms, Mapping}
import services.TransactionType

object TransactionTypeMapper {


  implicit val appTypeFormatter = new Formatter[TransactionType.TransactionType] {

    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], TransactionType.TransactionType] = {
      data.get(key).map { value =>
        try {
          Right(TransactionType.withName(value))
        } catch {
          case e: NoSuchElementException => error(key, value + " is not a valid transaction type")
        }
      }.getOrElse(error(key, "No transaction type provided."))
    }

    private def error(key: String, msg: String) = Left(List(new FormError(key, msg)))

    override def unbind(key: String, value: TransactionType.TransactionType): Map[String, String] = {
      Map(key -> value.toString())
    }
  }

  def transactionType: Mapping[TransactionType.TransactionType] = Forms.of[TransactionType.TransactionType]
}
