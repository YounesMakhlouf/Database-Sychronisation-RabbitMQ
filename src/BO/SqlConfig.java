package BO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SqlConfig {
    public static String getScript() throws IOException {
        StringBuilder scriptContent = new StringBuilder();
        String line;
        BufferedReader reader = new BufferedReader(new FileReader("product_sales_changes.sql"));
        while ((line = reader.readLine()) != null) {
            scriptContent.append(line).append("\n");
        }
        reader.close();

        return scriptContent.toString();


    }
}
