package com.ohgiraffers.section02.template;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTemplate {
    public static Connection getConnection() {
        Connection con = null;
        Properties prop = new Properties();


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
        }

        /* 설명. connection을 닫는 개념은 별도로 분리하고 실제로 닫는 시점을 나중에 우리가 추후 sevice계층에서 진행할 예정 */
        return con;  // 위 과정은 Service와 Repo를 한꺼번에 해치우는 과정이다.
    }

    public static void close(Connection con) {
        try {
            if (con != null && !con.isClosed()) con.close();   // 객체가 생성되어있으나 닫혀있지 않다면 닫는 예외
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
