import java.util.*;
import java.io.Serializable;

public abstract class Transaction implements Serializable {
    protected String itemName;
    protected Date transactionDate;
    protected double amount;
    protected String[] categories;
    protected Wallet wallet;

    public Transaction(String itemName, Date transactionDate, double amount, Wallet wallet, String[] categories) {
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

}
