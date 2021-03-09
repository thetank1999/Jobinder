/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Admin
 */
public class JDBCUtils {

    public static Connection getConnection() {
        try {
            return ((DataSource) new InitialContext().lookup("java:comp/env/AssignmentDB")).getConnection();
        } catch (SQLException | NamingException e) {
            System.out.println(e.getCause());
        }
        return null;
    }

}
