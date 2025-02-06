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
        if (List.of(name, surname, gender).stream().anyMatch(s -> s == null || s.isEmpty())) {
            return "Invalid input. Please provide valid name, surname, and gender.";
        }

        User user = new User(name, surname, gender.equalsIgnoreCase("male"));
        return repo.createUser(user) ? "User was created successfully." : "User creation failed. Please try again.";
    }

    @Override
    public String getUserById(int id) {
        return (id <= 0) ? "Invalid user ID." :
                repo.getUserById(id) != null ? repo.getUserById(id).toString() : "User not found.";
    }

    @Override
    public String getAllUsers() {
        return repo.getAllUsers().stream()
                .map(User::toString)
                .collect(Collectors.collectingAndThen(Collectors.joining("\n"),
                        result -> result.isEmpty() ? "No users available." : result));
    }
}
