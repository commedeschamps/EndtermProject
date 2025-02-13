package factories;

import models.Product;
import models.ProductCreationRequest;

public class ProductFactory implements Factory<Product> {

    @Override
    public Product create(Object request) {
        if (request instanceof ProductCreationRequest) {
            ProductCreationRequest req = (ProductCreationRequest) request;

            int id = req.getId().intValue();
            double price = req.getPrice().doubleValue();

            return new Product(id, req.getName(), req.getDescription(), price, req.getQuantity(), req.getCategory());
        } else {
            throw new IllegalArgumentException("Invalid request type");
        }
    }
}
