package controllers;

import controllers.interfaces.ICartController;
import models.Cart;
import models.OrderItem;
import repositories.interfaces.ICartRepository;

public class CartController implements ICartController {
    private final ICartRepository repo;

    public CartController(ICartRepository repo) {
        this.repo = repo;
    }

    @Override
    public String addItemToCart(int userId, int productId, int quantity, double price) {
        OrderItem item = new OrderItem(0, productId, quantity, price);
        boolean added = repo.addItemToCart(userId, item);
        return (added) ? "Item added to cart" : "Failed to add item to cart";
    }

    @Override
    public String getCartByUserId(int userId) {
        Cart cart = repo.getCartByUserId(userId);
        return (cart == null) ? "Cart not found" : cart.toString();
    }

    @Override
    public String clearCart(int userId) {
        boolean cleared = repo.clearCart(userId);
        return (cleared) ? "Cart cleared" : "Failed to clear cart";
    }
}
