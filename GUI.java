import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GUI {
    public static void main(String[] args) {
        Map<String, Wallet> wallets = new HashMap<>();
        final String[] currentWalletName = { null };

        JFrame frame = new JFrame("Finance Tracker");
        frame.setSize(450, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Wallet UI
        JLabel walletLabel = new JLabel("Select Wallet:");
        walletLabel.setBounds(20, 10, 100, 30);
        JComboBox<String> walletDropdown = new JComboBox<>();
        walletDropdown.setBounds(120, 10, 150, 30);

        JButton addWalletBtn = new JButton("Add Wallet");
        addWalletBtn.setBounds(50, 50, 120, 30);     
        JButton removeWalletBtn = new JButton("Remove Wallet");
        removeWalletBtn.setBounds(180, 50, 120, 30);

        frame.add(walletLabel);
        frame.add(walletDropdown);
        frame.add(addWalletBtn);
        frame.add(removeWalletBtn);

        // Action Buttons
        JButton addIncome = new JButton("Add Income");
        JButton addExpense = new JButton("Add Expense");
        JButton viewReports = new JButton("View Reports");
        JButton totalBalance = new JButton("Total Balance");
        JButton viewTransactions = new JButton("Transaction History");

        addIncome.setBounds(100, 100, 200, 30);
        addExpense.setBounds(100, 140, 200, 30);
        viewReports.setBounds(100, 180, 200, 30);
        totalBalance.setBounds(100, 220, 200, 30);
        viewTransactions.setBounds(100, 260, 200, 30);

        frame.add(addIncome);
        frame.add(addExpense);
        frame.add(viewReports);
        frame.add(totalBalance);
        frame.add(viewTransactions);

        walletDropdown.addActionListener(e -> {
            currentWalletName[0] = (String) walletDropdown.getSelectedItem();
        });

        addWalletBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(frame, "Enter wallet name:");
            if (name != null && !name.trim().isEmpty() && !wallets.containsKey(name)) {
                wallets.put(name, new Wallet());
                walletDropdown.addItem(name);
                walletDropdown.setSelectedItem(name);
                currentWalletName[0] = name;
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid or duplicate wallet name.");
            }
        });

        removeWalletBtn.addActionListener(e -> {
            String selected = (String) walletDropdown.getSelectedItem();
            if (selected != null) {
                wallets.remove(selected);
                walletDropdown.removeItem(selected);
                currentWalletName[0] = null;
            }
        });

        // Add Income
        addIncome.addActionListener(e -> {
            if (currentWalletName[0] == null) {
                JOptionPane.showMessageDialog(frame, "Please select a wallet first.");
                return;
            }
            Wallet wallet = wallets.get(currentWalletName[0]);

            JFrame incomeFrame = new JFrame("Add Income");
            incomeFrame.setSize(320, 220);
            incomeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            incomeFrame.setLayout(null);

            JLabel incomeLabel = new JLabel("Income Amount:");
            JTextField incomeField = new JTextField();

            JLabel categoryLabel = new JLabel("Category:");
            String[] incomeOptions = { "Salary", "Bonus", "Investment", "Gift" };
            JComboBox<String> incomeDropdown = new JComboBox<>(incomeOptions);

            JButton saveIncome = new JButton("Save Income");

            incomeLabel.setBounds(20, 20, 100, 30);
            incomeField.setBounds(130, 20, 150, 30);
            categoryLabel.setBounds(20, 60, 100, 30);
            incomeDropdown.setBounds(130, 60, 150, 30);
            saveIncome.setBounds(90, 110, 120, 30);

            incomeFrame.add(incomeLabel);
            incomeFrame.add(incomeField);
            incomeFrame.add(categoryLabel);
            incomeFrame.add(incomeDropdown);
            incomeFrame.add(saveIncome);

            saveIncome.addActionListener(ev -> {
                String incomeAmount = incomeField.getText();
                if (incomeAmount.isEmpty()) {
                    JOptionPane.showMessageDialog(incomeFrame, "Please enter an income amount.");
                    return;
                }
                try {
                    double amount = Double.parseDouble(incomeAmount);
                    if (amount < 0)
                        throw new NumberFormatException();

                    String category = (String) incomeDropdown.getSelectedItem();
                    String[] categories = { category };

                    Income income = new Income("Income", amount, LocalDateTime.now(), wallet, categories);
                    wallet.addTransaction(income);

                    JOptionPane.showMessageDialog(incomeFrame, "Income saved.");
                    incomeFrame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(incomeFrame, "Enter a valid positive amount.");
                }
            });

            incomeFrame.setVisible(true);
        });

        // Add Expense
        addExpense.addActionListener(e -> {
            if (currentWalletName[0] == null) {
                JOptionPane.showMessageDialog(frame, "Please select a wallet first.");
                return;
            }
            Wallet wallet = wallets.get(currentWalletName[0]);

            JFrame expenseFrame = new JFrame("Add Expense");
            expenseFrame.setSize(320, 250);
            expenseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            expenseFrame.setLayout(null);

            JLabel expenseLabel = new JLabel("Expense Amount:");
            JTextField expenseField = new JTextField();

            JLabel itemNameLabel = new JLabel("Item Name:");
            JTextField itemNameField = new JTextField();

            JLabel categoryLabel = new JLabel("Category:");
            String[] expenseOptions = { "Groceries", "Transport", "Bills", "Entertainment", "Health", "Other" };
            JComboBox<String> expenseDropdown = new JComboBox<>(expenseOptions);

            JButton saveExpense = new JButton("Save Expense");

            expenseLabel.setBounds(20, 20, 100, 30);
            expenseField.setBounds(130, 20, 150, 30);
            itemNameLabel.setBounds(20, 60, 100, 30);
            itemNameField.setBounds(130, 60, 150, 30);
            categoryLabel.setBounds(20, 100, 100, 30);
            expenseDropdown.setBounds(130, 100, 150, 30);
            saveExpense.setBounds(90, 150, 120, 30);

            expenseFrame.add(expenseLabel);
            expenseFrame.add(expenseField);
            expenseFrame.add(itemNameLabel);
            expenseFrame.add(itemNameField);
            expenseFrame.add(categoryLabel);
            expenseFrame.add(expenseDropdown);
            expenseFrame.add(saveExpense);

            saveExpense.addActionListener(ev -> {
                String expenseAmount = expenseField.getText();
                String itemName = itemNameField.getText();

                if (expenseAmount.isEmpty() || itemName.isEmpty()) {
                    JOptionPane.showMessageDialog(expenseFrame, "Please fill in all fields.");
                    return;
                }

                try {
                    double amount = Double.parseDouble(expenseAmount);
                    if (amount < 0)
                        throw new NumberFormatException();

                    if ((wallet.getTotalExpense() + amount) > wallet.getTotalIncome()) {
                        JOptionPane.showMessageDialog(expenseFrame, "Insufficient balance.");
                        return;
                    }

                    String category = (String) expenseDropdown.getSelectedItem();
                    String[] categories = { category };

                    Expense expense = new Expense(itemName, LocalDateTime.now(), amount, wallet, categories);
                    wallet.addTransaction(expense);

                    JOptionPane.showMessageDialog(expenseFrame, "Expense saved.");
                    expenseFrame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(expenseFrame, "Enter a valid positive amount.");
                }
            });

            expenseFrame.setVisible(true);
        });

        // View Reports
        viewReports.addActionListener(e -> {
            if (currentWalletName[0] == null) {
                JOptionPane.showMessageDialog(frame, "Please select a wallet first.");
                return;
            }
            Wallet wallet = wallets.get(currentWalletName[0]);

            // --- Prepare data for Pie Chart (Spending Distribution) ---
            Map<String, Double> spendingByCategory = new HashMap<>();
            for (Transaction t : wallet.getTransactions()) {
                if (t instanceof Expense) {
                    for (String cat : t.getCategories()) {
                        spendingByCategory.put(cat, spendingByCategory.getOrDefault(cat, 0.0) + t.getAmount());
                    }
                }
            }

            DefaultPieDataset pieDataset = new DefaultPieDataset();
            for (Map.Entry<String, Double> entry : spendingByCategory.entrySet()) {
                pieDataset.setValue(entry.getKey(), entry.getValue());
            }
            JFreeChart pieChart = ChartFactory.createPieChart("Spending Distribution",pieDataset,true, true, false);

            // --- Prepare data for Bar Chart (Top Expenses) ---
            // Collect top 5 expenses by amount
            List<Expense> expenses = wallet.getTransactions().stream().filter(t -> t instanceof Expense).map(t -> (Expense) t).sorted((a, b) -> Double.compare(b.getAmount(), a.getAmount())).limit(5).collect(Collectors.toList());

            DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
            for (Expense exp : expenses) {
                // Use itemName as category label
                barDataset.addValue(exp.getAmount(), "Expense Amount", exp.getSummary());
            }

            JFreeChart barChart = ChartFactory.createBarChart("Top 5 Expenses","Item","Amount",barDataset,PlotOrientation.VERTICAL,false, true, false);

            // --- Show charts in a JFrame ---
            JFrame chartFrame = new JFrame("Expense Reports");
            chartFrame.setSize(800, 600);
            chartFrame.setLayout(new GridLayout(1, 2)); // side by side

            ChartPanel piePanel = new ChartPanel(pieChart);
            ChartPanel barPanel = new ChartPanel(barChart);

            chartFrame.add(piePanel);
            chartFrame.add(barPanel);

            chartFrame.setLocationRelativeTo(frame);
            chartFrame.setVisible(true);
        });

        // Total Balance
        totalBalance.addActionListener(e -> {
            if (currentWalletName[0] == null) {
                JOptionPane.showMessageDialog(frame, "Please select a wallet first.");
                return;
            }

            Wallet wallet = wallets.get(currentWalletName[0]);

            double totalIncome = wallet.getTotalIncome();
            double totalExpense = wallet.getTotalExpense();
            double balance = wallet.getCurrentBalance();

            String report = "Total Income: " + totalIncome + "\n"+ "Total Expenses: " + totalExpense + "\n"+ "Balance: " + balance;
            JOptionPane.showMessageDialog(frame, report);
        });
        viewTransactions.addActionListener(e -> {
            if (currentWalletName[0] == null) {
                JOptionPane.showMessageDialog(frame, "Please select a wallet first.");
                return;
            }

            Wallet wallet = wallets.get(currentWalletName[0]);
            List<Transaction> transactions = wallet.getTransactions();

            if (transactions.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No transactions available.");
                return;
            }

            String[] columnNames = { "Summary", "Amount", "Date", "Type", "Category" };
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (Transaction t : transactions) {
                String type = (t instanceof Income) ? "Income" : "Expense";
                model.addRow(new Object[] {t.getSummary(),t.getAmount(),t.getTransactionDate().toString(),type,String.join(", ", t.getCategories())
                });
            }

            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new java.awt.Dimension(600, 300));

            JOptionPane.showMessageDialog(frame, scrollPane, "Transaction History", JOptionPane.INFORMATION_MESSAGE);
        });

        frame.setVisible(true);
    }

}
