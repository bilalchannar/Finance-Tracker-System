// report/ReportInterface.java
import java.time.LocalDateTime;
import java.util.List;

public interface ReportInterface {
    void generateReport(List<Transaction> transactions, LocalDateTime start, LocalDateTime end);
}
