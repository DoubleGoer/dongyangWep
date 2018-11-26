package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	public UserDAO() {
	try {
		String dbURL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8";
		String dbID = "root";
		String dbPassword = "123456";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		}catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	/* Name : login �޼ҵ� 
	 * ����    : db�ȿ� ������ �˻��Ͽ� userid�� password�� ��ġ�ϴ� ������ �����͸� �����ش�
	 * 1. conn.preStatement�� ���� db�� ������ ���ش�. 
	 * 2. setString(1,userID) ù��° ? ��ġ�� userID ���� �Է����ش�.
	 * 3. return ���� ���� �� ���� �� 1�� ��ȯ���� �޾��� ��쿡�� ���������� ��ȯ (�������� �ش� ��ȯ�� ���� ������ϴ�)
	 * 4. rs.getString(1)�� 1��° �����͸� �����ͼ� POST������ ����� userPassword ���̶����Ѵ�.
	 */
	public int login(String userID,String userPassword) {
		String SQL = "SELECT userPassword FROM users WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			//SQL���� ����ǥ �κ��� ����Ű�� �� 1�� ù��° ����ǥ��  userID ���� �ְڴٴ� �ǹ�
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1;//��й�ȣ ��ġ
				}
				else
					return 0;//��й�ȣ ����ġ
			}
			return -1; // ID�� �������� ����
		}catch(Exception e) {
			e.printStackTrace();
		}
		//�����ͺ��̽� ����
		return -2;
	}
	/* Name : join method
	 * ���� : ȸ�����Խ� �����͸� SQL���� ������� DB�� �־��ִ� ����
	 * 1. ��ü User�� �޾� db�� ���¸� �˸°� �޾��ش�
	 * 2. setString�� �̿��� ���ŷο� insert ���� ���¸� ������
	 * 3. -1 ���� ��ȯ�Ǹ� �����ͺ��̽� ������ �߻��Ѱ�
	 * 4. sql���� ���������� ����ȴٸ� -1 �̿ܿ� ������ ��ȯ �ȴ�.
	 */
	
	public int join(User user) {
		String SQL = "Insert into users values(?,?,?,?,?);";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;//db ����
	}
}
 