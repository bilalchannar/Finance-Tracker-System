import java.util.Date;

public class Expense extends Transaction {
    public Expense(String itemName, Date transactionDate, double amount, Wallet wallet) {
		super(itemName, transactionDate, amount, wallet);
	}

	@Override
	public void calculate() {
		
	}
}
