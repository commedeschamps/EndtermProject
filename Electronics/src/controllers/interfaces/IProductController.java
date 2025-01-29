package controllers.interfaces;

public interface IProductController {
    String createProduct(String name, String description, double price, int quantity, String category);
    String getProductById(int id);
    String getAllProducts();
}
