package dao;

import model.Book;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookDAO {
    private final String BOOK_FILE;

    public BookDAO(String fileName) {
        this.BOOK_FILE = fileName;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(BOOK_FILE);
             Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                String author = parts[2];
                boolean available = Boolean.parseBoolean(parts[3]);
                books.add(new Book(id, title, author, available));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void saveBooks(List<Book> books) {
        // Not implemented for simplicity. You can adapt this method if needed.
    }
}
