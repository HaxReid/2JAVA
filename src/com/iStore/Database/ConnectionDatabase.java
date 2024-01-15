package com.iStore.Database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Description -> ConnectionDatabase class
 *
 * @Author benjamin, Pierre et Léonard
 */
public class ConnectionDatabase {
    static final String driver = "com.mysql.cj.jdbc.Driver";
    //static final String url = "jdbc:mysql://localhost:3306/iStore";
    //static String username = "root";
    //static String password = "";

    static final String url = "jdbc:mysql://192.168.56.56:3306/iStore";
    static String username = "homestead";
    static String password = "secret";

    Connection conn = null;
    Statement statement = null;
    ResultSet resultSet = null;

    /**
     * Description -> connection to database
     *
     * @Author benjamin, Pierre et Léonard
     */
    public ConnectionDatabase(){
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            statement = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Description -> Connection to database
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @return
     */
    public Connection getConn() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
