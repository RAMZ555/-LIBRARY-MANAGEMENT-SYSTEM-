package dao;

import model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDAO {
    private final String USER_FILE;

    public UserDAO(String fileName) {
        this.USER_FILE = fileName;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(USER_FILE);
             Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                users.add(new User(id, name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void saveUsers(List<User> users) {
        // Not implemented for simplicity. You can adapt this method if needed.
    }
}
