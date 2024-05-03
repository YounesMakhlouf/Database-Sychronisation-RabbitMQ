import SQL.MySQLConnection;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProductCRUD {

    public static ArrayList<Product> getAll(String db_name) {
        String sql = "SELECT * FROM product_sales";
        ArrayList<Product> products = new ArrayList<>();
        try (var conn = MySQLConnection.connect(db_name); var stmt = conn.createStatement(); var rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                LocalDate date = rs.getObject("date", LocalDate.class);

                products.add(new Product(date, rs.getString("region"), rs.getString("product"), rs.getInt("quantity"), rs.getDouble("cost"), rs.getDouble("amount"), rs.getDouble("tax"), rs.getDouble("total")));
            }
        } catch (SQLException ex) {
            System.err.println("Error occurred while fetching products: " + ex.getMessage());
        }
        return products;
    }


    public static void add(Product product, String db_name) {
        String sql = "INSERT INTO product_sales(date, region, product, quantity, cost, amount, tax, total) VALUES(?,?,?,?,?,?,?,?)";

        try (var conn = MySQLConnection.connect(db_name); var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // set parameters for statement
            stmt.setObject(1, product.getDate());
            stmt.setString(2, product.getRegion());
            stmt.setString(3, product.getProduct());
            stmt.setInt(4, product.getQty());
            stmt.setDouble(5, product.getCost());
            stmt.setDouble(6, product.getAmount());
            stmt.setDouble(7, product.getTax());
            stmt.setDouble(8, product.getTotal());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Successfully added product " + product);
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while adding a product: " + e.getMessage());
        }
    }
}
