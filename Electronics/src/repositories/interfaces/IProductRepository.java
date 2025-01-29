package repositories.interfaces;

import models.Product;

import java.util.List;

public interface IProductRepository {
    boolean createProduct(Product product);
    Product getProductById(int id);
    List<Product> getAllProducts();

    void updateProduct(Product product);

    void deleteProduct(int id);
}