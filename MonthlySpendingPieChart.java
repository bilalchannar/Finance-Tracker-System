import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class MonthlySpendingPieChart implements ReportInterface {

    @Override
    public void generateReport(List<Transaction> transactions, LocalDateTime start, LocalDateTime end) {
        // Step 2: Filter expenses by date range
        List<Transaction> filteredExpenses = new ArrayList<>();

        for (Transaction t : transactions) {
            if (!t.getTransactionDate().isBefore(start) && !t.getTransactionDate().isAfter(end)) {
                if (t instanceof Expense) {
                    filteredExpenses.add(t);
                }
            }
        }

        // Step 3: Aggregate spending by category
        Map<String, Double> spendingByCategory = new HashMap<>();

        for (Transaction expense : filteredExpenses) {
            for (String category : ((Expense) expense).getCategories()) {
                spendingByCategory.put(
                    category,
                    spendingByCategory.getOrDefault(category, 0.0) + ((Expense) expense).getAmount()
                );
            }
        }

        // Step 4: Calculate total spending and display report
        double totalSpending = 0.0;
        for (double amount : spendingByCategory.values()) {
            totalSpending += amount;
        }

        if (totalSpending == 0) {
            System.out.println("No expenses found in this period.");
        } else {
            System.out.println("Monthly Spending Pie Chart Report:");
            for (Map.Entry<String, Double> entry : spendingByCategory.entrySet()) {
                String category = entry.getKey();
                double amount = entry.getValue();
                double percentage = (amount / totalSpending) * 100;
                System.out.printf("%s: $%.2f (%.2f%%)%n", category, amount, percentage);
            }
        }
    }
}
