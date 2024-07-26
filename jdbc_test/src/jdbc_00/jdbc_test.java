package jdbc_00;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		/*여기까지 데이터베이스 연결하는 것으로 이름,명칭,포트번호 등을 통해서 불러오기*/


		//	connection 	연결
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		/*여기는 연결하고 쿼리실행 후 결과값 담기까지가 */
		try {
			Class.forName(driver);
			/*접속*/
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
			/*데이터베이스 연결 실패들은 모두 SQLException에 들어감 */
		}

		try {

			String sql ="SELECT school_id, school_name, school_area " /*띄어쓰기 필수*/
					+ "FROM tb_school_info ";

			//	    +"WHERE school_id = 1";

			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			List<HashMap<String, Object>> list = new ArrayList();

			/*prepareStatement = sql ="SELECT school_id, school_name, school_area " 
			 * + "FROM tb_school_info "
					   				+"WHERE school_id = 1";
					   담겨있음*/

			//			int schoolId = 0;
			//			String schoolName = null;
			//			String schoolArea = null;


			while (rs.next()) {
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("schoolId", rs.getInt(1));
				rsMap.put("schoolName", rs.getString(2));
				rsMap.put("schoolArea", rs.getString(3));
				list.add(rsMap);

				//				schoolId = rs.getInt(1);
				//				schoolName = rs.getString("school_name");
				//				schoolArea = rs.getString(3);
			}
			//			System.out.println("schoolId :"+schoolId);
			//			System.out.println("schoolName :"+schoolName);
			//			System.out.println("schoolArea :"+schoolArea);
			System.out.println("학교ID / t학교이름 / t학교지역");
			for(int i = 0; i<list.size(); i++) {
				System.out.println(list.get(i).get("schoolId").toString()+"\t"
						+list.get(i).get("schoolName").toString()+"\t"
						+list.get(i).get("schoolArea").toString());

			}
		}catch (SQLException e){
			//TODO handle exception
			System.out.println("error : "+e);
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








