package com.ohgiraffers.section01.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        /* 설명. 트랜잭션 처리를 위한 DBMS 연동용 Connection 객체 생성 */
        Connection con = getConnection();

        /* 설명. 해당 Connection을 통해 트랜잭션 처리(비즈니스 로직 처리, CRUD 등) */
        Statement stmt = null; // 조회가 아니면 int 조회면 Resultset, 쿼리를 운반하고 결과를 반환
        ResultSet rs = null;   // 조회를 할 예정(DML작업이면 ResultSet 대신 int로 처리)

        try {
            // 쿼리 실을 트럭 생성
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM EMPLOYEE");

            // 한 행을 가리키고 있다. rs는 한 행만 가리킨다.
            // 단일행 조회면 if문으로 충분하다
            while (rs.next()) {
                /* 설명. while문 안의 rs는 한 행을 의미한다.(돌 때마다 한 행싹) */
                System.out.print(rs.getString("EMP_NAME"));
                System.out.println(rs.getInt("SALARY"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            /* 설명. 생성과 달리 역순으로 각 스트림을 닫는다. */
            if (rs != null) {
                close(rs);
            }
            if (stmt != null) {
                close(stmt);
            }
            if (con != null) {
                close(con);
            }
        }
    }
}
