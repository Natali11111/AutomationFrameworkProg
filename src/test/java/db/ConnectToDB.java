package db;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectToDB {
    private static String URL_DB;
    private static String USERNAME;
    private static String PASSWORD;
    public static Connection CONNECTION;
    public static Statement STATEMENT;


    @BeforeSuite
    public void preparingConnectionToDB() {
        URL_DB = "jdbc:mysql://localhost:3306/db";
        USERNAME = "user";
        PASSWORD = "password";
        try {
            CONNECTION = DriverManager.getConnection(URL_DB, USERNAME, PASSWORD);
            STATEMENT = CONNECTION.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterSuite
    public void closeConnectionWithDB() {
        try {
            CONNECTION.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
