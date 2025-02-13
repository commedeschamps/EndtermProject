package factories;

public class UserCreationRequest {
    private Integer id;
    private String name;
    private String surname;
    private Boolean gender;
    private String email;

    public UserCreationRequest(Integer id, String name, String surname, Boolean gender, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Boolean getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }
}
