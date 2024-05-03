package BO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class SqlConfig {
    public static String getScript() throws IOException {
        try (var lines = Files.lines(Paths.get("product_sales_changes.sql"))) {
            return lines.collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new IOException("Failed to read SQL script file.", e);
        }
    }
}