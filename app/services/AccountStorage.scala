package services

trait AccountStorage {
  def addAccount(account: Account): Boolean
  def getAccount(accountName: String): Option[Account]
  def addTransaction(accountName: String, trs: Transaction): Boolean
  def getTransactions(accountName: String): List[Transaction]
}
