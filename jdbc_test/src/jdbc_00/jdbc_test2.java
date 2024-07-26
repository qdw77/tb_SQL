package jdbc_00;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class jdbc_test2 {

	public static void main(String[] args) {
		// 우송고등학교에 재학중인 학생정보를 출력하시오.
		// TODO Auto-generated method stub
				final String driver = "org.mariadb.jdbc.Driver";
				final String db_ip = "localhost";
				final String db_port = "3306";
				final String db_name = "student_test";
				final String db_url = 
						"jdbc:mariadb://"+db_ip+":"+db_port+"/"+db_name;
				
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				try {
					Class.forName(driver);
					conn = DriverManager.getConnection(db_url, "root", "4623");
					if(conn != null) {
						System.out.println("접속성공");
					}
				}catch(ClassNotFoundException e) {
					System.out.println("드라이버 로드 실패");
					e.printStackTrace();
				}catch(SQLException e) {
					System.out.println("접속 실패");
					e.printStackTrace();
				}
				
				try {
					String sql ="SELECT school.school_id as schoolId,\r\n"
							+ "	school.school_name as schoolName,\r\n"
							+ "    school.school_area as schoolArea,\r\n"
							+ "    student.student_name as studentName,\r\n"
							+ "    '재학중' as graduate\r\n" /*if(student.student_graduate_yn = 'Y', '졸업생', '재학생') as '재학 여부'*/
							+ "    FROM tb_school_info school\r\n"
							+ "    INNER JOIN tb_student_info student\r\n"
							+ "    on school.school_id = student.school_id\r\n"
							+ "    and student.student_graduate_yn = 'N'\r\n"
							+ "    WHERE school.school_name ='우송고등학교';"/*"    WHERE school.school_name =?;"*/
							;
					//우송고등학교에 재학 중인 학생들을 출력
					//쿼리를 작성해서 입력
//					String sql = "";
					System.out.println(sql);
					
					pstmt = conn.prepareStatement(sql);
//					pstmt.setString(1, "우송고등학교");
					
					rs = pstmt.executeQuery();
					List<HashMap<String, Object>> list = new ArrayList();
					
					while(rs.next()) {
						HashMap<String, Object> rsMap = new HashMap<String, Object>();
						//조회결과 HashMap에 추가
						rsMap.put("schoolId", rs.getInt("schoolId"));
						rsMap.put("schoolName", rs.getString("schoolName"));
						rsMap.put("schoolArea", rs.getString("schoolArea"));
						rsMap.put("studentName", rs.getString("studentName"));
						rsMap.put("graduate", rs.getString("graduate"));	
						
//						rsMap.put("schoolId", rs.getInt(1));
//						rsMap.put("schoolName", rs.getString(2));
//						rsMap.put("schoolArea", rs.getString(3));
//						rsMap.put("studentName", rs.getString(4));
//						rsMap.put("graduate", rs.getString("재학 여부"));	<as 값을 넣었을 때 안 넣었다면 if 값을 전부 넣거나 아예 안 넣었다면 5로 작성해도 괜찮다		
						list.add(rsMap);

					}
					System.out.println("학교ID\t학교이름\t       학교지역\t학생이름\t졸업여부");
					for(int i = 0; i<list.size(); i++) {
						//list에 있는 값 출력
						System.out.print(list.get(i).get("schoolId").toString()+"\t");
						System.out.print(list.get(i).get("schoolName").toString()+"\t");
						System.out.print(list.get(i).get("schoolArea").toString()+"\t");
						System.out.print(list.get(i).get("studentName").toString()+"\t");
						System.out.println(list.get(i).get("graduate").toString()+"\t");
							
					
					}
				}catch (SQLException e) {
					// TODO: handle exception
					System.out.println("error :" + e);
				}finally {
					try {
						if(rs != null) {
							rs.close();
						}
						
						if(pstmt != null) {
							pstmt.close();
						}
						if(conn != null && conn.isClosed()) {
							conn.close();
						}
					
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
	}
	
}
