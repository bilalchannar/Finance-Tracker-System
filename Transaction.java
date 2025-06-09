
import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Transaction implements Serializable {
    protected String itemName;
    protected LocalDateTime transactionDate;
    protected double amount;
    protected String[] categories;//Why? To allow categorization of transactions
    protected Wallet wallet;//Why? To associate the transaction with a specific wallet

    public Transaction(String itemName, LocalDateTime transactionDate, double amount, Wallet wallet, String[] categories) {
        this.itemName = itemName;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.wallet = wallet;
        this.categories = categories;
    }
    abstract void calculate();

    public String getSummary() {
        return itemName + ": " + amount + " on " + transactionDate;

    }
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    public double getAmount() {
        return amount;
    }
    public String[] getCategories() {
        return categories;
    }

}
