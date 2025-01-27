package User;

import model.User;
import java.util.*;

public class UserRepository {
    private final List<User> users = new ArrayList<>();
    private int nextId = 1;

    public void registerUser(User user) {
        user.setId(nextId++);
        users.add(user);
    }

    public Optional<User> loginUser(String email, String password) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst();
    }

    public User getUserById(int userId) {
        return users.stream()
                .filter(user -> user.getId() == userId)
                .findFirst()
                .orElse(null);
    }

    public void updateUserBalance(int userId, double newBalance) {
        User user = getUserById(userId);
        if (user != null) {
            user.setBalance(newBalance);
        }
    }
    public Optional<User> getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

}
