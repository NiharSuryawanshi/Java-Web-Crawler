package web_crawler;
import java.sql.*;


public class database_con {
	public Connection con = null;
	
	public database_con() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/crawler";
			con = DriverManager.getConnection(url, "root", "root");
			System.out.println("Connection established");
		}
		catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	public ResultSet runSql1(String sql) throws SQLException{
		Statement st = con.createStatement();
		return st.executeQuery(sql);
	}
	
	public boolean runSql2(String sql) throws SQLException{
		Statement st = con.createStatement();
		return st.execute(sql);
	}
	
	protected void finalize() throws Throwable {
		if (con != null || !con.isClosed()) {
			con.close();
		}
	}
}
