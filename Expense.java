import java.util.Date;

public class Expense extends Transaction {

    public Expense(String itemName, Date transactionDate, double amount, Wallet wallet, String[] categories) {
        super(itemName, transactionDate, amount, wallet, categories);
        calculate(); // Optionally apply expense immediately
    }

    @Override
    public void calculate() {
        wallet.setInitialStartingBalance(wallet.getInitialStartingBalance() - amount);
    }

    @Override
    public String getSummary() {
        return "EXPENSE - " + super.getSummary();
    }
}
