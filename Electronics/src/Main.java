import controllers.*;
import controllers.interfaces.*;
import data.PostgresDB;
import data.interfaceces.IDB;
import repositories.*;
import repositories.interfaces.*;
import java.util.Date;


public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "1234", "electronics_shop");

        IUserRepository userRepository = new UserRepository(db);
        IProductRepository productRepository = new ProductRepository(db);
        IOrderRepository orderRepository = new OrderRepository(db);
        IOrderItemRepository orderItemRepository = new OrderItemRepository(db);
        ICartRepository cartRepository = new CartRepository(db);

        IUserController userController = new UserController(userRepository);
        IProductController productController = new ProductController(productRepository);
        IOrderController orderController = new OrderController(orderRepository);
        IOrderItemController orderItemController = new OrderItemController(orderItemRepository);
        ICartController cartController = new CartController(cartRepository);

        ShopApplication app = new ShopApplication(
                userController,
                productController,
                orderController,
                orderItemController,
                cartController
        );

        app.start();

        db.close();
    }
}
