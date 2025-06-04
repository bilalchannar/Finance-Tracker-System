import java.util.*;
import java.time.LocalDateTime;

public class MonthlyTopExpensesBarChart implements ReportInterface {

    private static final int TOP_N = 5; // Show top 5 expenses

    @Override
    public void generateReport(List<Transaction> transactions, LocalDateTime start, LocalDateTime end) {
        // Step 1: Filter only expenses in the date range
        List<Expense> filteredExpenses = new ArrayList<>();

        for (Transaction t : transactions) {
            if (t instanceof Expense) {
                LocalDateTime date = t.getTransactionDate();
                if ((date.isEqual(start) || date.isAfter(start)) &&
                    (date.isEqual(end) || date.isBefore(end))) {
                    filteredExpenses.add((Expense) t);
                }
            }
        }

        // Step 2: Sort expenses by amount (descending)
        filteredExpenses.sort((e1, e2) -> Double.compare(e2.getAmount(), e1.getAmount()));

        // Step 3: Display top N expenses
        System.out.println("Top " + TOP_N + " Expenses Report:");
        int count = 0;
        for (Expense e : filteredExpenses) {
            if (count >= TOP_N) break;
            System.out.printf("%s | Amount: $%.2f | Date: %s%n",
                e.getItemName(), e.getAmount(), e.getTransactionDate());
            count++;
        }

        if (count == 0) {
            System.out.println("No expenses found in this time period.");
        }
    }
}
