# Finance Tracker System

## Classes
1. `FinanceTracker`
2. `Wallet`
3. `Transaction` (abstract)
4. `Income` (extends `Transaction`)
5. `Expense` (extends `Transaction`)
6. `FilesManager`
7. `DataContainer`
8. `ReportInterface`
9. `MonthlySpendingPieChart` (implements `ReportInterface`)
10. `MonthlyTopExpensesBarChart` (implements `ReportInterface`)

## Attributes & Methods
- FinanceTracker
  - Attributes:
    - Inherits from JFrame
  - Methods:
    - void main(String[] args)

- Wallet
  - Attributes:
    - String title
    - double[] spendingLimits
    - double initialStartingBalance
  - Methods:
    - double getTotalSpent()
    - void addTransaction(Transaction t)

- Transaction (abstract)
  - Attributes:
    - String itemName
    - double valueAmount
    - DateTime occuredOn
    - String[] categories
    - Wallet wallet
  - Methods:
    - abstract void calculate()
    - String getSummary()

- Income (extends Transaction)
  - Methods:
    - calculate(): Overrides

- Expense (extends Transaction)
  - Methods:
    - calculate(): Overrides

- FilesManager
  - Methods (static):
    - String[] listAvailableFiles()
    - DataContainer loadDataFromFile(String filePath)
    - boolean saveDataToFile(DataContainer data, String filePath)
    - boolean fileExists(String filePath)

- DataContainer (implements Serializable)
  - Attributes:
    - List<Wallet> wallets
    - List<Transaction> transactions
  - Methods:
    - addWallet(Wallet w)
    - addTransaction(Transaction t)

- ReportInterface
  - Methods:
    - generateReport(List<Transaction> transactions, DateTime startDate, DateTime endDate)

- MonthlySpendingPieChart (implements ReportInterface)
  - Methods:
    - generateReport(...)

- MonthlyTopExpensesBarChart (implements ReportInterface)
  - Methods:
    - generateReport(...)

## Relationships

| Relationship Type | Description |
| ----------------- | ----------- |
| Inheritance | Income and Expense inherit from abstract class Transaction |
| Interface Implementation | MonthlySpendingPieChart and MonthlyTopExpensesBarChart implement ReportInterface |
| Association | Transaction is associated with Wallet (each transaction is linked to a wallet) |
| | FinanceTracker uses Wallet and Transaction |
| | FinanceTracker uses FilesManager
| | FilesManager accesses (uses) DataContainer for persistence operations |
| Aggregation | FinanceTracker aggregates (has) DataContainer |
| Composition | DataContainer contains (composes) Wallet and Transaction (strong ownership) |

## Class Diagram
