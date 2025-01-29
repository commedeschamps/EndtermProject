package repositories.interfaces;

import models.Cart;
import models.OrderItem;

public interface ICartRepository {
    boolean addItemToCart(int userId, OrderItem item);
    Cart getCartByUserId(int userId);
    boolean clearCart(int userId);
}