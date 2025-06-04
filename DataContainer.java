import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataContainer implements Serializable {
    private List<Wallet> wallets;
    private List<Transaction> transactions;

    public DataContainer() {
        wallets = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public void addWallet(Wallet w) {
        wallets.add(w);
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
