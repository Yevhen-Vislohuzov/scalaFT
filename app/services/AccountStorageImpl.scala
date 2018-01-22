package services

import scala.collection.mutable


class AccountStorageImpl extends AccountStorage{
  private var accounts = mutable.Map[String, Account]()

  override def addAccount(account: Account): Boolean = {
    if (!accounts.contains(account.name)) {
      accounts += (account.name -> account)
      return true
    }
    false
  }

  override def getAccount(accountName: String): Option[Account] = accounts.get(accountName)

  override def addTransaction(accountName: String, trs: Transaction): Boolean = {
    val acc = accounts.get(accountName)
    if (acc.isDefined) {
      var remainingAmount = acc.get.amount

      trs.transactionType match {
        case TransactionType.CREDIT => remainingAmount -= trs.amount
        case TransactionType.DEBIT => remainingAmount += trs.amount
      }

      if (remainingAmount >= 0) {
        acc.get.amount = remainingAmount
        acc.get.transactions = trs :: acc.get.transactions
        return true
      }
    }
    false
  }

  override def getTransactions(accountName: String): List[Transaction] = {
    val acc = accounts.get(accountName)
    if (acc.isDefined) {
      return accounts(accountName).transactions
    }
    throw new IllegalArgumentException
  }
}
