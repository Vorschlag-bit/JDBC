package com.ohgiraffers.section03.sqlinjection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application2 {
    private static String empId = "200";
    private static String empName = "' OR 1=1 AND EMP_ID = '200";

    /* 설명.
    *  Statement와 달리 placeholder(?)를 통한 sql injection 공격을 막을 수 있게 작성되어 있어
    *   보안적 측면에서도 PreparedStatement가 우수하다.
    *  */
    public static void main(String[] args) {
        Connection con  = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM EMPLOYEE WHERE EMP_ID = ? AND EMP_NAME = ?";
        /* 설명. Placeholder(?)에 싱글쿼테이션(')이 들어가면 추가적으로 싱글 쿼테이션(')을 넣어 sql injection 공격을 막는다. */
//        SELECT * FROM EMPLOYEE WHERE EMP_ID ='200' AND EMP-NAME = ''' OR 1=1 AND EMP_ID = ''200'으로 쿼리가 날라간다.
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, empId);
            ps.setString(2, empName);

            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString("EMP_NAME") + "님 환영합니다.");
            } else {
                System.out.println("회원 정보가 없습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(ps);
            close(con);
        }
    }
}
