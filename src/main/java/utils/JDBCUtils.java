/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class JDBCUtils {

    private static final String JDBCURL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=SE1502_ASSIGNMENT_GROUP_7;";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "sa";

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(JDBCURL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getCause());
        }
        return null;
    }

}
