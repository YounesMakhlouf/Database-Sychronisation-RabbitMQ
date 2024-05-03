package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static Connection connect(String db_name) throws SQLException {
        Connection conn = null;
        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Get database credentials from SQL.DatabaseConfig class
            var jdbcUrl = DatabaseConfig.getDbUrl() + db_name;
            var user = DatabaseConfig.getDbUsername();
            var password = DatabaseConfig.getDbPassword();

            // Open a connection
            conn = DriverManager.getConnection(jdbcUrl, user, password);
            System.out.println("Successfully connected to the database" + db_name);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e);
        } catch (SQLException e) {
            System.err.println("SQLException caught during database connection attempt to " + db_name + e);
        }
        return conn;
    }
}
