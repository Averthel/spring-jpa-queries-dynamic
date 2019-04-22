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

        productDao.deleteByProducer("Radian");
        System.out.println("Products without Radian:");
        productDao.getAll().forEach(System.out::println);

        System.out.println("Opiekacze: ");
        List<Product> productByName = productDao.getByName("Opiekacz");
        System.out.println(productByName);

        ctx.close();
    }
}
