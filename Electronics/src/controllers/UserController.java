package controllers;

import controllers.interfaces.IUserController;
import models.User;
import repositories.interfaces.IUserRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserController implements IUserController {
    private final IUserRepository userRepository;

    public UserController(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String createUser(String email, String password, String name) {
        User existingUser = userRepository.getUserByEmail(email);
        if (existingUser != null) {
            return "User with this email already exists!";
        }

        String hashedPassword = hashPassword(password);
        if (hashedPassword == null) {
            return "Error in hashing password.";
        }

        User user = new User(email, hashedPassword, name);
        boolean isCreated = userRepository.createUser(user);
        return isCreated ? "User registered successfully" : "Registration failed";
    }

    @Override
    public String loginUser(String email, String password) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            return "Invalid email or password";
        }

        String hashedPassword = hashPassword(password);
        if (hashedPassword != null && hashedPassword.equals(user.getPassword())) {
            return "Login successful";
        } else {
            return "Invalid email or password";
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
