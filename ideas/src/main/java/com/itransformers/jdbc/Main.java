package com.itransformers.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by vasko on 06-02-2018.
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        try {
            NodeDAO dao = new NodeDAO(connection);
            dao.insertNode("1", new Date(), 3);
            dao.insertNode("2", new Date(), 4);
        } catch (Exception e) {
            connection.close();
        }
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/test";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","pass123");
        Connection conn = DriverManager.getConnection(url, props);
        return conn;
    }

}
