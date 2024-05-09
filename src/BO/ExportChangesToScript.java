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
            deleteChangesLog(BO_number);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // delete changes from product_sales_changes_log after reading them and exprting them to the file
    public static void deleteChangesLog(int BO_number) {
        String db_name = "BO" + BO_number;
        String query = "Delete FROM product_sales_changes_log";
        try (Connection connection = MySQLConnection.connect(db_name); Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(query);
            System.out.println(rowsAffected + " rows deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] argv) {
        if (argv.length != 1) {
            System.out.println("Usage: BO Number <bo" + "_number>");
            System.exit(1);
        }
        int boNumber = Integer.parseInt(argv[0]);
        exportProductSalesChangesToScript(boNumber);
    }
}