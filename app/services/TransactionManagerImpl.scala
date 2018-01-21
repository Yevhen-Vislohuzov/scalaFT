package services

import scala.collection.mutable


class TransactionManagerImpl extends TransactionManager {
  private val transactions = mutable.MutableList[Transaction]()

  override def addTransaction(trs: Transaction): Unit = transactions += trs

  override def getTransactions: mutable.MutableList[Transaction] = transactions
}
