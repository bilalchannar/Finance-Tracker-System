import java.time.LocalDateTime;
import java.util.*;
import java.util.Scanner;

public class FinanceTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = "finance_data.dat";

        System.out.println("Finance Tracker System");
        System.out.println("1. Load and View Existing Data");
        System.out.println("2. Add New Data and Save");
        System.out.print("Choose an option (1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            if (FilesManager.doesFileExist(fileName)) {
                DataContainer data = FilesManager.loadDataFromFile(fileName);
                System.out.println("\n✅ Data loaded successfully!");

                for (Wallet wallet : data.getWallets()) {
                    System.out.println("\nWallet: " + wallet.getTitle());
                    System.out.println("Balance: " + wallet.getInitialStartingBalance());
                    System.out.println("Total Spent: " + wallet.getTotalExpense());

                    List<Transaction> transactions = wallet.getTransactions();
                    System.out.println("Transactions:");
                    for (Transaction t : transactions) {
                        System.out.println(t.getSummary());
                    }
                }
            } else {
                System.out.println("\n❌ No saved data found.");
            }
        } else if (choice == 2) {
            double[] spendingLimits = {5000.0, 2000.0};
            Wallet myWallet = new Wallet("My Main Wallet", 10000.0, spendingLimits);

            String[] incomeCategories = {"Salary"};
            Income salary = new Income("June Salary", 5000.0, LocalDateTime.now(), myWallet, incomeCategories);
            myWallet.addTransaction(salary);

            String[] expenseCategories = {"Groceries"};
            Expense grocery = new Expense("Supermarket", LocalDateTime.now(), 1500.0, myWallet, expenseCategories);
            myWallet.addTransaction(grocery);

            DataContainer dataContainer = new DataContainer();
            dataContainer.addWallet(myWallet);
            dataContainer.addTransaction(salary);
            dataContainer.addTransaction(grocery);

            if (FilesManager.saveDataToFile(dataContainer, fileName)) {
                System.out.println("\n✅ Data saved successfully!");
            } else {
                System.out.println("\n❌ Failed to save data.");
            }

            // Generate Reports
            List<Transaction> allTransactions = myWallet.getTransactions();
            LocalDateTime start = LocalDateTime.now().minusMonths(1);
            LocalDateTime end = LocalDateTime.now();

            System.out.println("\n--- Monthly Spending Pie Chart ---");
            ReportInterface pieChart = new MonthlySpendingPieChart();
            pieChart.generateReport(allTransactions, start, end);

            System.out.println("\n--- Top Expenses Bar Chart ---");
            ReportInterface topExpensesChart = new MonthlyTopExpensesBarChart();
            topExpensesChart.generateReport(allTransactions, start, end);
        } else {
            System.out.println("\n❌ Invalid choice.");
        }

        scanner.close();
    }
}
