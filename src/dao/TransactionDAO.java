package dao;
import model.Transaction;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionDAO {
    private final String TRANSACTION_FILE;

    public TransactionDAO(String fileName) {
        this.TRANSACTION_FILE = fileName;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TRANSACTION_FILE);
             Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                int bookId = Integer.parseInt(parts[1]);
                int userId = Integer.parseInt(parts[2]);
                LocalDate issueDate = LocalDate.parse(parts[3]);
                LocalDate returnDate = parts[4].equals("null") ? null : LocalDate.parse(parts[4]);
                transactions.add(new Transaction(id, bookId, userId, issueDate, returnDate));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public void saveTransactions(List<Transaction> transactions) {
        // Not implemented for simplicity. You can adapt this method if needed.
    }
}
