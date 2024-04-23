import java.sql.Date;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Product kotkot = new Product(Date.valueOf("2002-09-19"), "East", "Kotkot", 1, 19, 19, 19, 19);
        ProductCRUD.add(kotkot,"sales");
        ArrayList<Product> products = ProductCRUD.getAll("sales");
        for (Product p : products) {
            System.out.println(p);
        }
    }
}