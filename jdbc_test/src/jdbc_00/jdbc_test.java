package jdbc_00;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class jdbc_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		//	SQL연결
		final String driver = "org.mariadb.jdbc.Driver";
		final String db_ip = "localhost"; /*데이터베이스 URL*/
		final String db_port = "3306"; /*데이터베이스 포트번호*/
		final String db_name = "student_test"; /*DB명*/
		final String db_url =
				"jdbc:mariadb://"+db_ip+":"+db_port+"/"+db_name;

		//	connection 	연결
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "4623");
			if(conn != null) {
				System.out.println("DB 접속성공");
				//				시스아웃 =System.out.println
			}

		}catch(ClassNotFoundException e){
			System.out.println("드라이버 모드 실패");
			e.printStackTrace();
		} catch(SQLException e) {
			System.out.println("DB접속 실패");
			e.printStackTrace();
		}

		try {

			String sql ="SELECT school_id, school_name, school_area " /*띄어쓰기 필수*/
					+ "FROM tb_school_info "
					+"WHERE school_id = 1";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);


			rs = pstmt.executeQuery();
			int schoolId = 0;
			String schoolName = null;
			String schoolArea = null;

			while (rs.next()) {
				schoolId = rs.getInt(1);
				schoolName = rs.getString("school_name");
				schoolArea = rs.getString(3);
			}
			System.out.println("schoolId :"+schoolId);
			System.out.println("schoolId :"+schoolName);
			System.out.println("schoolId :"+schoolArea);
		}catch (SQLException e){
			//
			System.out.println("error :"+e);
		}finally {

			try {
				if(rs != null ) {
					rs.close();
				}
				if(pstmt != null ) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}


			} catch(SQLException e) {
				e.printStackTrace();
				
			}
		}
	}

}








