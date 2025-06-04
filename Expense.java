import java.time.LocalDateTime;


public class Expense extends Transaction {

    public Expense(String itemName, LocalDateTime transactionDate, double amount, Wallet wallet, String[] categories) {
        super(itemName, transactionDate, amount, wallet, categories);
        calculate(); 
    }

    @Override
    public void calculate() {
        wallet.setInitialStartingBalance(wallet.getInitialStartingBalance() - amount);
    }

    @Override
    public String getSummary() {
        return "EXPENSE - " + super.getSummary();
    }
	public double getAmount() {
		return amount;
}
public String[] getCategories() {
		return categories;
}
public String getItemName() {
		return itemName;

}
}