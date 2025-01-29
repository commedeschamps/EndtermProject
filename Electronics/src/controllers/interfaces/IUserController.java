package controllers.interfaces;

public interface IUserController {
    String createUser(String email, String password, String name);
    String loginUser(String email, String password);
}
