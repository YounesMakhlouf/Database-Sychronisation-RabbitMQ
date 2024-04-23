package BO;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import SQL.MySQLConnection;
public class ExportChangesToScript {

    public static void exportProductSalesChangesToScript(int BO_number) {
        String db_name="BO"+BO_number;

        try (Connection connection = MySQLConnection.connect(db_name)) {
            // Create statement
            Statement statement = connection.createStatement();

            // Query to fetch data from product_sales_changes_log
            String query = "SELECT query FROM product_sales_changes_log";

            // Execute query
            ResultSet resultSet = statement.executeQuery(query);

            // Initialize script content
            StringBuilder scriptContent = new StringBuilder();

            // Iterate over rows and append queries to script content
            while (resultSet.next()) {
                scriptContent.append(resultSet.getString("query")).append("\n");
            }

            // Get script directory
            String scriptDir = System.getProperty("user.dir");

            // Specify file path
            String filePath = scriptDir + "/product_sales_changes.sql";

            // Write script content to file
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(scriptContent.toString());
            }

            System.out.println("Captured changes exported to script: product_sales_changes.sql");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int i = 1;
        exportProductSalesChangesToScript(i);
    }
}
