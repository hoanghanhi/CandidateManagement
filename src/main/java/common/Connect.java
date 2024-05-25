package common;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
    private static Connection connection = null;
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://DESKTOP-PKOM4GN:1433;databaseName=Candidate_Management;encrypt=false";
            String userName = "sa";
            String password = "123456";

            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        return connection;
    }
}
