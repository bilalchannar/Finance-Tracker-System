import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Wallet implements Serializable {
    private String title;
    private double[] spendingLimits;
    private double initialStartingBalance;
    private List<Transaction> transactions = new ArrayList<>();
    public Wallet() {
        this.title = "Default Wallet";
        this.initialStartingBalance = 0.0;
        this.spendingLimits = new double[]{1000.0, 500.0}; // Default limits
    }

    public Wallet(String title, double initialStartingBalance, double[] spendingLimits) {
        this.title = title;
        this.initialStartingBalance = initialStartingBalance;
        this.spendingLimits = spendingLimits;
    }

    public double getTotalExpense() {
        double total = 0.0;
        for (Transaction t : transactions) {
            if (t instanceof Expense) {
                total += t.amount;
            }
        }
        return total;
    }
    public double getTotalIncome() {
        double total = 0.0;
        for (Transaction t : transactions) {
            if (t instanceof Income) {
                total += t.amount;
            }
        }
        return total;
    }
    public double getCurrentBalance() {
        return initialStartingBalance + getTotalIncome() - getTotalExpense();
    }

    public void addTransaction(Transaction t) {
        t.calculate();
        transactions.add(t);
    }
     public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getInitialStartingBalance() {
        return initialStartingBalance;
    }

    public void setInitialStartingBalance(double balance) {
        this.initialStartingBalance = balance;
    }
    public String getTitle() {
        return title;
    }

    // Optional: Getters for title, limits, or transaction list
}
