package factories;

import models.User;
import factories.UserCreationRequest;

public class UserFactory implements Factory<User> {

    @Override
    public User create(Object request) {
        if (request instanceof UserCreationRequest) {
            UserCreationRequest req = (UserCreationRequest) request;
            return new User(req.getId(), req.getName(), req.getSurname(), req.getGender(), req.getEmail());
        } else {
            throw new IllegalArgumentException("Invalid request type");
        }
    }
}
