import java.util.Date;

public class Income extends Transaction {

    // Constructor
    public Income(String itemName, double amount, Date transactionDate, Wallet wallet, String[] categories) {
        super(itemName, transactionDate, amount, wallet, categories);
        calculate(); // Apply the income to the wallet immediately
    }

    // Adds the income amount to the wallet balance
    @Override
    public void calculate() {
        wallet.setInitialStartingBalance(wallet.getInitialStartingBalance() + amount);
    }

    // Optional: Clearer summary
    @Override
    public String getSummary() {
        return "INCOME - " + super.getSummary();
    }
}
