package akm.ex;

import java.sql.*;

public class Mydbc {
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/";

	private static final String default_database = "呵呵";
	private static final String default_username = "SB";
	private static final String default_password = "123456";

	private String db_name;
	private String db_username;
	private String db_password;

	public Mydbc() { this(default_username, default_password); }
	public Mydbc(String username, String password) {
		this(username, password, default_database);
	}
	public Mydbc(String username, String password, String database) {
		db_username = username;
		db_password = password;
		db_name = database;
	}

	public interface MydbcExecute{
		public String execute(Statement stmt) throws SQLException;
	}

	public String query(MydbcExecute mydbcExecute) {
		String s = "";
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL+db_name+"?useSSL=false&serverTimezone=UTC", db_username, db_password);
			stmt = conn.createStatement();
			s = mydbcExecute.execute(stmt);
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return s;
	}

	public int login (String username, String password) {
		String s = query((stmt)->{
			ResultSet rs = stmt.executeQuery(
"SELECT * FROM user WHERE username='"+username+"' AND password='"+password+"'"
			);
			if (rs.next())
				return "true";
			else
				return "false";
		});
		return s.compareTo(""+true)==0 ? 1 : 0;
	}

	public static void main(String[] args) {
		Mydbc mdb = new Mydbc();
		println(mdb.login("admin", "admi"));
		
	}

	static <T> void print(T t) { System.out.print(t); }
	static <T> void println() { System.out.println(); }
	static <T> void println(T t) { System.out.println(t); }
	static void printf(String fmt, Object... args) { System.out.printf(fmt, args); }
}

