package controllers.interfaces;

public interface ICartController {
    String addItemToCart(int userId, int productId, int quantity, double price);
    String getCartByUserId(int userId);
    String clearCart(int userId);
}