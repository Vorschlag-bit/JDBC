package com.ohgiraffers.section01.prepared;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

/* 설명.
  *  PreparedStatement는 Statement와 달리 최초 한 번 쿼리를 파싱하고 컴파일하고 캐싱하여 다시 재해석하는 과정(비용)을
  *   생략함으로써 인해 불완전하게 작성된 쿼리 실행의 경우는 Statement보다 빠르다.
  *  */
public class Application02 {
    public static void main(String[] args) {

        Connection con = getConnection();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("사번을 입력하세요: ");
        String empId = sc.nextLine();

        String entYn = "N";
        /* 설명. PreparedStatement는 Statement와 달리 placeHolder(?)를 활용한 하나의 문자열 형태로 쿼리를 작성 */
        String query = "SELECT EMP_ID, EMP_NAME FROM EMPLOYEE WHERE EMP_ID = ? AND EMP_YN = ?";
        // ?는 placeorder라는 곳으로 뭐가 들어갈지 준비되어 있는 곳이다.
        // Statement는 완벽히 준비된 형태로 출발하고, pstmt는 덜 준비된 상태로 출발한다.
        // 나중에 완성되는 형태지만 두 번째 쿼리부터는 속도가 훨씬 빠르다.

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, empId);
            pstmt.setString(2, entYn);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString("EMP_ID")
                + "," + rs.getString("EMP_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
    }
}
