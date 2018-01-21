package services

import scala.collection.mutable

trait TransactionManager {
  def getTransactions: mutable.MutableList[Transaction]
  def addTransaction(trs: Transaction): Unit

}
