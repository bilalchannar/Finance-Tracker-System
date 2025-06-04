import java.util.*;
import java.io.Serializable;

public abstract class  Transaction implements Serializable {
    protected String itemName;
    protected Date transactionDate;
    protected double amount;
    protected Wallet wallet;

    public Transaction(String itemName, Date transactionDate, double amount, Wallet wallet) {
        this.itemName = itemName;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.wallet = wallet;
    }
    abstract void calculate();

    public String getSummary() {
        return itemName + ": " + amount + " on " + transactionDate;

}

}
