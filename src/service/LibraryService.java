package service;

import dao.BookDAO;
import dao.TransactionDAO;
import dao.UserDAO;
import model.Book;
import model.Transaction;
import model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LibraryService {
    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private final TransactionDAO transactionDAO;

    public LibraryService() {
        this.bookDAO = new BookDAO("resources/bookstext.txt");
        this.userDAO = new UserDAO("resources/userstext.txt");
        this.transactionDAO = new TransactionDAO("resources/transactionstext.txt");
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }

    public void addBook(Book book) {
        List<Book> books = getAllBooks();
        books.add(book);
        bookDAO.saveBooks(books);
    }

    public void addUser(User user) {
        List<User> users = getAllUsers();
        users.add(user);
        userDAO.saveUsers(users);
    }

    public void issueBook(int bookId, int userId) {
        List<Book> books = getAllBooks();
        Optional<Book> bookOpt = books.stream().filter(b -> b.getId() == bookId && b.isAvailable()).findFirst();
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setAvailable(false);
            bookDAO.saveBooks(books);

            List<Transaction> transactions = getAllTransactions();
            Transaction transaction = new Transaction(transactions.size() + 1, bookId, userId, LocalDate.now(), null);
            transactions.add(transaction);
            transactionDAO.saveTransactions(transactions);
        }
    }

    public void returnBook(int bookId, int userId) {
        List<Book> books = getAllBooks();
        Optional<Book> bookOpt = books.stream().filter(b -> b.getId() == bookId && !b.isAvailable()).findFirst();
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setAvailable(true);
            bookDAO.saveBooks(books);

            List<Transaction> transactions = getAllTransactions();
            Optional<Transaction> transactionOpt = transactions.stream()
                    .filter(t -> t.getBookId() == bookId && t.getUserId() == userId && t.getReturnDate() == null)
                    .findFirst();
            if (transactionOpt.isPresent()) {
                Transaction transaction = transactionOpt.get();
                transaction.setReturnDate(LocalDate.now());
                transactionDAO.saveTransactions(transactions);
            }
        }
    }
}
