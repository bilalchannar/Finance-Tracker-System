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
  - Attributes: Inherits from JFrame
  - Methods:
    - main(String[] args)

- Wallet
  - Attributes:
    - String title
    - double[] spendingLimits
    - double initialStartingBalance
  - Methods:
    - double getTotalSpent()
    - void addTransaction(Transaction t)