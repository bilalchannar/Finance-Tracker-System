import java.io.Serializable;

public class Wallet implements Serializable {
    private String title;
    private double[] spendingLimits;
    private double initialStartingBalance;

    public double getTotalSpend() {
        // TODO: Implement logic to calculate total spend
        return 0.0;
    }

    public void addTransaction(Transaction t) {
        
    }
}