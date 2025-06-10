import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.io.*;



// ---------------- Model Classes ----------------

abstract class Transaction implements Serializable {
    protected String itemName;
    protected LocalDateTime transactionDate;
    protected double amount;
    protected String[] categories;
    protected Wallet wallet;

    public Transaction(String itemName, double amount, LocalDateTime date, String[] categories, Wallet wallet) {
        this.itemName = itemName;
        this.amount = amount;
        this.transactionDate = date;
        this.categories = categories;
        this.wallet = wallet;
    }

    public abstract void calculate();

    public String getSummary() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return itemName + ": " + amount + " on " + transactionDate.format(fmt);
    }

    public double getAmount() {
        return amount;
    }

    public String getItemName() {
        return itemName;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String[] getCategories() {
        return categories;
    }

    public String getType() {
        return this instanceof Income ? "Income" : "Expense";
    }
}

class Income extends Transaction {
    public Income(String itemName, double amount, LocalDateTime date, String[] categories, Wallet wallet) {
        super(itemName, amount, date, categories, wallet);
        calculate();
    }

    @Override
    public void calculate() {
        wallet.setInitialStartingBalance(wallet.getInitialStartingBalance() + amount);
    }
}

class Expense extends Transaction {
    public Expense(String itemName, double amount, LocalDateTime date, String[] categories, Wallet wallet) {
        super(itemName, amount, date, categories, wallet);
        calculate();
    }

    @Override
    public void calculate() {
        wallet.setInitialStartingBalance(wallet.getInitialStartingBalance() - amount);
    }
}

class Wallet implements Serializable {
    private String title;
    private double initialStartingBalance;
    private double[] spendingLimits;
    private List<Transaction> transactions = new ArrayList<>();

    public Wallet(String title, double initialBalance, double[] spendingLimits) {
        this.title = title;
        this.initialStartingBalance = initialBalance;
        this.spendingLimits = spendingLimits;
    }

    public String getTitle() {
        return title;
    }

    public double getInitialStartingBalance() {
        return initialStartingBalance;
    }

    public void setInitialStartingBalance(double balance) {
        this.initialStartingBalance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public double getTotalSpend() {
        return transactions.stream()
                .filter(t -> t instanceof Expense)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    @Override
    public String toString() {
        return title + " (Balance: " + String.format("%.2f", initialStartingBalance) + ")";
    }
}

class DataContainer implements Serializable {
    private List<Wallet> wallets = new ArrayList<>();

    public List<Wallet> getWallets() {
        return wallets;
    }

    public void addWallet(Wallet w) {
        wallets.add(w);
    }
}

class FilesManager {
    public static final String DATADIR = "data";

    public static boolean saveDataToFile(DataContainer data, String filename) {
        File folder = new File(DATADIR);
        if (!folder.exists()) folder.mkdirs();
        File file = new File(folder, filename);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static DataContainer loadDataFromFile(String filename) {
        File file = new File(DATADIR, filename);
        if (!file.exists()) return new DataContainer();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (DataContainer) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new DataContainer();
        }
    }
}

// ---------------- GUI Application ----------------

public class Project extends JFrame {

    private DefaultListModel<Wallet> walletListModel = new DefaultListModel<>();
    private JList<Wallet> walletJList = new JList<>(walletListModel);
    private DefaultTableModel transactionTableModel;
    private JTable transactionTable;

    private DataContainer dataContainer;
    private final String dataFileName = "finance_data.dat";

    public Project() {
        setTitle("Finance Tracker");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        dataContainer = FilesManager.loadDataFromFile(dataFileName);
        for (Wallet w : dataContainer.getWallets()) walletListModel.addElement(w);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Left Panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new TitledBorder("Wallets"));
        walletJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane walletScroll = new JScrollPane(walletJList);
        leftPanel.add(walletScroll, BorderLayout.CENTER);

        JPanel walletButtons = new JPanel();
        JButton addWalletBtn = new JButton("Add Wallet");
        JButton deleteWalletBtn = new JButton("Delete Wallet");
        walletButtons.add(addWalletBtn);
        walletButtons.add(deleteWalletBtn);
        leftPanel.add(walletButtons, BorderLayout.SOUTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new TitledBorder("Transactions"));
        transactionTableModel = new DefaultTableModel(new String[] {"Type", "Item", "Amount", "Date", "Categories"}, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        transactionTable = new JTable(transactionTableModel);
        JScrollPane transactionScroll = new JScrollPane(transactionTable);
        centerPanel.add(transactionScroll, BorderLayout.CENTER);

        JPanel transactionButtons = new JPanel();
        JButton addIncomeBtn = new JButton("Add Income");
        JButton addExpenseBtn = new JButton("Add Expense");
        JButton deleteTransactionBtn = new JButton("Delete Transaction");
        transactionButtons.add(addIncomeBtn);
        transactionButtons.add(addExpenseBtn);
        transactionButtons.add(deleteTransactionBtn);
        centerPanel.add(transactionButtons, BorderLayout.SOUTH);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        JButton saveBtn = new JButton("Save Data");
        JButton loadBtn = new JButton("Load Data");
        JButton reportBtn = new JButton("Generate Summary");
        bottomPanel.add(saveBtn);
        bottomPanel.add(loadBtn);
        bottomPanel.add(reportBtn);

        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        leftPanel.setPreferredSize(new Dimension(250, getHeight()));

        walletJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) loadTransactionsForSelectedWallet();
        });

        addWalletBtn.addActionListener(e -> showAddWalletDialog());
        deleteWalletBtn.addActionListener(e -> deleteSelectedWallet());
        addIncomeBtn.addActionListener(e -> showAddTransactionDialog(true));
        addExpenseBtn.addActionListener(e -> showAddTransactionDialog(false));
        deleteTransactionBtn.addActionListener(e -> deleteSelectedTransaction());

        saveBtn.addActionListener(e -> {
            boolean success = FilesManager.saveDataToFile(dataContainer, dataFileName);
            JOptionPane.showMessageDialog(this, success ? "Data saved successfully." : "Failed to save data.");
        });

        loadBtn.addActionListener(e -> {
            dataContainer = FilesManager.loadDataFromFile(dataFileName);
            walletListModel.clear();
            for (Wallet w : dataContainer.getWallets()) walletListModel.addElement(w);
            transactionTableModel.setRowCount(0);
            JOptionPane.showMessageDialog(this, "Data loaded successfully.");
        });

        reportBtn.addActionListener(e -> showSummaryReport());
        if (!walletListModel.isEmpty()) walletJList.setSelectedIndex(0);
    }

    private void loadTransactionsForSelectedWallet() {
        transactionTableModel.setRowCount(0);
        Wallet wallet = walletJList.getSelectedValue();
        if (wallet == null) return;
        for (Transaction t : wallet.getTransactions()) {
            transactionTableModel.addRow(new Object[]{
                    t.getType(),
                    t.getItemName(),
                    String.format("%.2f", t.getAmount()),
                    t.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    String.join(", ", t.getCategories())
            });
        }
    }

    private void showAddWalletDialog() {
        JTextField titleField = new JTextField();
        JTextField balanceField = new JTextField();
        JTextField limitsField = new JTextField();
        Object[] message = {
                "Wallet Title:", titleField,
                "Initial Balance:", balanceField,
                "Spending Limits (comma separated):", limitsField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Wallet", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String title = titleField.getText().trim();
                double balance = Double.parseDouble(balanceField.getText().trim());
                double[] limits = Arrays.stream(limitsField.getText().trim().split(","))
                        .filter(s -> !s.isBlank()).mapToDouble(Double::parseDouble).toArray();
                Wallet wallet = new Wallet(title, balance, limits);
                dataContainer.addWallet(wallet);
                walletListModel.addElement(wallet);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input, try again.");
            }
        }
    }

    private void deleteSelectedWallet() {
        Wallet wallet = walletJList.getSelectedValue();
        if (wallet == null) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Delete selected wallet?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dataContainer.getWallets().remove(wallet);
            walletListModel.removeElement(wallet);
            transactionTableModel.setRowCount(0);
        }
    }

    private void showAddTransactionDialog(boolean isIncome) {
        Wallet wallet = walletJList.getSelectedValue();
        if (wallet == null) {
            JOptionPane.showMessageDialog(this, "Please select a wallet.");
            return;
        }

        JTextField itemField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField categoriesField = new JTextField();

        Object[] message = {
                (isIncome ? "Income" : "Expense") + " Item:", itemField,
                "Amount:", amountField,
                "Categories (comma separated):", categoriesField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add " + (isIncome ? "Income" : "Expense"), JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String item = itemField.getText().trim();
                double amount = Double.parseDouble(amountField.getText().trim());
                String[] cats = Arrays.stream(categoriesField.getText().split(",")).map(String::trim).toArray(String[]::new);
                Transaction t = isIncome ? new Income(item, amount, LocalDateTime.now(), cats, wallet)
                        : new Expense(item, amount, LocalDateTime.now(), cats, wallet);
                wallet.addTransaction(t);
                loadTransactionsForSelectedWallet();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input, try again.");
            }
        }
    }

    private void deleteSelectedTransaction() {
        Wallet wallet = walletJList.getSelectedValue();
        int row = transactionTable.getSelectedRow();
        if (wallet == null || row == -1) {
            JOptionPane.showMessageDialog(this, "Select a transaction to delete.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Delete selected transaction?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            wallet.getTransactions().remove(row);
            loadTransactionsForSelectedWallet();
        }
    }

    private void showSummaryReport() {
        Wallet wallet = walletJList.getSelectedValue();
        if (wallet == null) {
            JOptionPane.showMessageDialog(this, "Select a wallet.");
            return;
        }

        double income = wallet.getTransactions().stream().filter(t -> t instanceof Income).mapToDouble(Transaction::getAmount).sum();
        double expense = wallet.getTransactions().stream().filter(t -> t instanceof Expense).mapToDouble(Transaction::getAmount).sum();

        JOptionPane.showMessageDialog(this, String.format("Wallet: %s\nTotal Income: %.2f\nTotal Expense: %.2f\nBalance: %.2f",
                wallet.getTitle(), income, expense, wallet.getInitialStartingBalance()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Project().setVisible(true));
    }
}
