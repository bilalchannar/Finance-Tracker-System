import java.time.LocalDateTime;

public class Income extends Transaction {

    public Income(String itemName, double amount, LocalDateTime transactionDate, Wallet wallet, String[] categories) {
        super(itemName, transactionDate, amount, wallet, categories);
        calculate(); 
    }


    @Override
    public void calculate() {
        wallet.setInitialStartingBalance(wallet.getInitialStartingBalance() + amount);
    }

    @Override
    public String getSummary() {
        return "INCOME - " + super.getSummary();
    }
}
