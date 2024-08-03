package com.ohgiraffers.section01.connection;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Application2 {
    public static void main(String[] args) {
        Properties prop = new Properties();
        Connection con = null;

        try {
            prop.load(new FileReader
                    ("src/main/java/com/ohgiraffers/section01/connection/jdbc-config.properties"));
            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            Class.forName(driver);
            DriverManager.getConnection(url, user, password);
            System.out.println("con = " + con);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
//            try {
//                if (con != null) con.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
            close(con);
        }
    }

    private static void close(Connection con) {
        try {
            if (con != null && !con.isClosed()) con.close();   // 객체가 생성되어있으나 닫혀있지 않다면 닫는 예외
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
