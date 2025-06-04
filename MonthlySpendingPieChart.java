// report/MonthlySpendingPieChart.java
import java.time.LocalDateTime;
import java.util.List;
public class MonthlySpendingPieChart implements ReportInterface {
    @Override
    public void generateReport(List<Transaction> transactions, LocalDateTime start, LocalDateTime end) {
        // TODO: implement actual pie chart (you can print for now)
        System.out.println("Pie Chart Report Placeholder");
    }
}
