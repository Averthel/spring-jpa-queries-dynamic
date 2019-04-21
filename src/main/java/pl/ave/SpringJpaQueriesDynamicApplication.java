package pl.ave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.ave.dao.ProductDao;
import pl.ave.model.Product;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringJpaQueriesDynamicApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringJpaQueriesDynamicApplication.class, args);

        List<Product> products = new ArrayList<>();
        products.add(new Product("Pralka", "Amiko", 1299.99));
        products.add(new Product("Frytkownica", "Amaro", 99.99));
        products.add(new Product("Opiekacz", "Amaro", 59.99));
        products.add(new Product("Mikrofalówka", "Radian", 299.99));
        products.add(new Product("Lodówka", "Radian", 2299.99));

        ProductDao productDao = ctx.getBean(ProductDao.class);
        products.forEach(productDao::save);

        System.out.println("All products:");
        List<Product> allProducts = productDao.getAll();
        allProducts.forEach(System.out::println);

        System.out.println("Products more expensive than 100");
        List<Product> expensiveProducts = productDao.customGet("SELECT p FROM Product p WHERE p.price > 100");
        expensiveProducts.forEach(System.out::println);

        System.out.println("All products ordered by price");
        List<Product> productsByPrice = productDao.customGet("SELECT p FROM Product p ORDER BY p.price ASC");
        productsByPrice.forEach(System.out::println);

        System.out.println("Expensive Radian Procuts");
        List<Product> expensiveRadianProducts = productDao.customGet
                ("SELECT p FROM Product p WHERE p.producer = 'Radian' AND p.price > 2000");
        expensiveRadianProducts.forEach(System.out::println);

        productDao.deleteALl();

        ctx.close();
    }
}
