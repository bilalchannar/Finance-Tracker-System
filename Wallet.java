import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Wallet implements Serializable {
    private String title;
    private double[] spendingLimits;
    private double initialStartingBalance;
    private List<Transaction> transactions = new ArrayList<>();

    // Constructor (optional)
    public Wallet(String title, double initialStartingBalance, double[] spendingLimits) {
        this.title = title;
        this.initialStartingBalance = initialStartingBalance;
        this.spendingLimits = spendingLimits;
    }

    public double getTotalSpend() {
        double total = 0.0;
        for (Transaction t : transactions) {
            if (t instanceof Expense) {
                total += t.amount;
            }
        }
        return total;
    }

    public void addTransaction(Transaction t) {
        t.calculate();
        transactions.add(t);
    }

    public double getInitialStartingBalance() {
        return initialStartingBalance;
    }

    public void setInitialStartingBalance(double balance) {
        this.initialStartingBalance = balance;
    }

    // Optional: Getters for title, limits, or transaction list
}
