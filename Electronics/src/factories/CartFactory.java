package factories;

import models.Cart;
import data.interfaceces.IDB;

public class CartFactory implements Factory<Cart> {
    private IDB database;

    public CartFactory(IDB database) {
        this.database = database;
    }

    @Override
    public Cart create(Object request) {
        if (request instanceof CartCreationRequest) {
            CartCreationRequest req = (CartCreationRequest) request;
            if (req.getCartId() != null) {
                return new Cart(req.getCartId());
            } else {
                return new Cart();
            }
        }
        throw new IllegalArgumentException("Invalid request type");
    }

}
