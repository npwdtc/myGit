package dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import dto.Player;

public class DataDB implements Data {

	// 驱动程序名
	private static String DRIVER = "com.mysql.jdbc.Driver";
	// URL指向要访问的数据库名game
	private static String DB_URL = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8";
	// MySQL配置时的用户名
	private static String DB_USER = "root";
	// MySQL配置时的密码
	private static String DB_PWD = "";

	private static String LOAD_SQL = "SELECT user_name,point FROM game ORDER BY point DESC LIMIT 0,5";
	private static String SAVE_SQL = "insert into game(user_name,point) values (?,?)";
	static {
		// 加载驱动程序
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	//从数据库中提取数据
	@Override
	public List<Player> loadData() {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Player> players = new ArrayList<Player>();
		try {
			// 连续数据库
			conn = (Connection) DriverManager.getConnection(DB_URL, DB_USER,
					DB_PWD);
			pst = (PreparedStatement) conn.prepareStatement(LOAD_SQL);
			rs = pst.executeQuery();

			while (rs.next()) {
				Player pla = new Player(rs.getString(1), rs.getInt(2));
				players.add(pla);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pst, conn);
		}
		return players;
	}

	//把数据存入数据库
	@Override
	public void saveData(Player pla) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			// 连续数据库
			conn = (Connection) DriverManager.getConnection(DB_URL, DB_USER,
					DB_PWD);
			pst = (PreparedStatement) conn.prepareStatement(SAVE_SQL);
			pst.setString(1, pla.getName());
			pst.setInt(2, pla.getPoint());
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pst, conn);
		}

	}

	//关闭数据库连接
	public static void close(ResultSet rs, Statement sta, Connection con) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (sta != null)
				sta.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) {
//		new DataDB().saveData(new Player("张三", 33));
//	}
}
