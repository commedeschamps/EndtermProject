//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public enum UserRole {
    ADMIN,
    USER,
    MODERATOR;

    private UserRole() {
    }

    public static UserRole fromString(String role) {
        for(UserRole userRole : values()) {
            if (userRole.name().equalsIgnoreCase(role)) {
                return userRole;
            }
        }
//test aaa
        throw new IllegalArgumentException("Unknown role: " + role);
    }
}
