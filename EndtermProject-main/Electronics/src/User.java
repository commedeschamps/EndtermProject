//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class User {
    private int id;
    private String name;
    private String email;
    private double balance;
    private UserRole role;

    public User(int id, String name, String email, double balance, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.role = role;
    }
//test
    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public double getBalance() {
        return this.balance;
    }

    public UserRole getRole() {
        return this.role;
    }
}
