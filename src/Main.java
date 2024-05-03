import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Product kotkot = new Product(LocalDate.of(1000, 9, 19), "East", "Kotou", 1, 19.0, 19.0, 19.0, 19.0);
        ProductCRUD.add(kotkot, "sales");
        ArrayList<Product> products = ProductCRUD.getAll("sales");

        products.forEach(System.out::println);
    }
}