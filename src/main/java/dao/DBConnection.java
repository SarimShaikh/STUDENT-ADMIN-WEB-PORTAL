
package dao;

/**
 * Created by Sarim on 4/17/2020.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection con;

    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_portal", "root", "");
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (con == null) {
            new DBConnection();
        }
        return con;
    }

}
