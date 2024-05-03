package BO;

import SQL.MySQLConnection;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ExportChangesToScript {

    public static void exportProductSalesChangesToScript(int BO_number) {
        String db_name = "BO" + BO_number;
        String query = "SELECT query FROM product_sales_changes_log";
        Path scriptFilePath = Paths.get(System.getProperty("user.dir"), "product_sales_changes.sql");
        StringBuilder scriptContent = new StringBuilder();
        try (Connection connection = MySQLConnection.connect(db_name); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query); FileWriter writer = new FileWriter(scriptFilePath.toFile())) {

            // Iterate over rows and append queries to script content
            while (resultSet.next()) {
                scriptContent.append(resultSet.getString("query")).append("\n");
            }

            writer.write(scriptContent.toString());

            System.out.println("Captured changes exported to script: " + scriptFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int i = 2;
        exportProductSalesChangesToScript(i);
    }
}