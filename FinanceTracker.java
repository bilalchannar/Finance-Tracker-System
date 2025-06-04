import java.util.Date;

public class FinanceTracker {
    public static void main(String[] args) {

        // 1. Create a wallet
        double[] spendingLimits = {5000.0, 2000.0}; // Optional categories
        Wallet myWallet = new Wallet("My Main Wallet", 10000.0, spendingLimits);

        // 2. Create an income transaction
        String[] incomeCategories = {"Salary"};
        Income salary = new Income("June Salary", 5000.0, new Date(), myWallet, incomeCategories);
        myWallet.addTransaction(salary); // This also calls calculate()

        // 3. Create an expense transaction
        String[] expenseCategories = {"Groceries"};
        Expense grocery = new Expense("Supermarket", new Date(), 1500.0, myWallet, expenseCategories);
        myWallet.addTransaction(grocery); // Also calls calculate()

        // 4. Print wallet balance
        System.out.println("Wallet Balance: " + myWallet.getInitialStartingBalance());

        // 5. Print total spending
        System.out.println("Total Spent: " + myWallet.getTotalSpend());

        // 6. Print transaction summaries
        System.out.println(salary.getSummary());
        System.out.println(grocery.getSummary());
    }
}
