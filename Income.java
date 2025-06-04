import java.util.Date;

public class Income extends Transaction {

	public Income(String itemName, Date transactionDate, double amount, Wallet wallet) {
		super(itemName, transactionDate, amount, wallet);
	}

	@Override
	public void calculate() {
		// my comment
	}
}