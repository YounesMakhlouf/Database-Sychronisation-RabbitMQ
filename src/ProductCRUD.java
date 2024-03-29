import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductCRUD {

    public static ArrayList<Product> getAll() {
        String sql = "SELECT * " + "FROM product_sales";
        ArrayList<Product> products = new ArrayList<Product>();
        try (var conn = MySQLConnection.connect(); var stmt = conn.createStatement(); var rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                products.add(new Product(rs.getDate("date"), rs.getString("region"), rs.getString("product"), rs.getInt("quantity"), rs.getDouble("cost"), rs.getDouble("amount"), rs.getDouble("tax"), rs.getDouble("total")));
//                System.out.println(rs.getString("date") + "\t" + rs.getString("region") + "\t" + rs.getString("product") + "\t" + rs.getString("quantity") + "\t" + rs.getString("cost") + "\t" + rs.getString("amount") + "\t" + rs.getString("tax") + "\t" + rs.getString("total"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return products;
    }

    public static void add(Product product) {
        String sql = "INSERT INTO product_sales VALUES(default,?,?,?,?,?,?,?,?)";

        try (var conn = MySQLConnection.connect(); var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // set parameters for statement
            stmt.setDate(1, product.getDate());
            stmt.setString(2, product.getRegion());
            stmt.setString(3, product.getProduct());
            stmt.setInt(4, product.getQty());
            stmt.setDouble(5, product.getCost());
            stmt.setDouble(6, product.getAmount());
            stmt.setDouble(7, product.getTax());
            stmt.setDouble(8, product.getTotal());

            int rowAffected = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}