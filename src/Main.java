import java.sql.Date;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Product> products = ProductCRUD.getAll();
        for (Product p : products) {
            System.out.println(p);
        }
        Product kotkot = new Product(Date.valueOf("2002-09-19"),  // Use appropriate date constructor
                "East", "Kotkot", 1, 19, 19, 19, 19);
        ProductCRUD.add(kotkot);
    }
}