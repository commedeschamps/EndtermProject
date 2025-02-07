package controllers;

import controllers.interfaces.IUserController;
import models.User;
import repositories.interfaces.IUserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserController implements IUserController {
    private final IUserRepository repo;

    public UserController(IUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createUser(String name, String surname, String gender) {
        if (name == null || name.trim().isEmpty()) {
            return "Invalid input: Name cannot be empty.";
        }
        if (surname == null || surname.trim().isEmpty()) {
            return "Invalid input: Surname cannot be empty.";
        }
        if (gender == null || gender.trim().isEmpty() ||
                !(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female"))) {
            return "Invalid input: Gender must be 'male' or 'female'.";
        }

        User user = new User(name.trim(), surname.trim(), gender.equalsIgnoreCase("male"));
        return repo.createUser(user) ? "User was created successfully." : "User creation failed. Please try again.";
    }

    @Override
    public String getUserById(int id) {
        if (id <= 0) {
            return "Invalid user ID. It must be a positive number.";
        }

        User user = repo.getUserById(id);
        return (user != null) ? user.toString() : "User not found.";
    }

    @Override
    public String getAllUsers() {
        List<User> users = repo.getAllUsers();
        if (users == null || users.isEmpty()) {
            return "No users available.";
        }

        return users.stream()
                .map(User::toString)
                .collect(Collectors.joining("\n"));
    }
}
