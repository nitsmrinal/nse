package nsedownload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil {
	private static Connection connection;


	private static DBUtil instance;

	private DBUtil() {

	}

	public static Connection getConnection(String db) {

		String className = "";
		String dbUrl="";
		if (!"".equals(db) && db.equals("POSTGRESS"))
		{
			//"jdbc:postgresql://hostname:port/dbname","username", "password");
			className = "org.postgresql.Driver";
//			dbUrl = "jdbc:postgresql://localhost:5432/postgress,postgress,sys";
//			dbUrl = "jdbc:postgresql://localhost:5432/postgres,postgres,123";
		//	dbUrl="jdbc:postgressql://localhost:5432/postgres?user=postgres&password=123";
			//jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true
		}
		else if (!"".equals(db) && db.equals("MYSQL")){

//			Class.forName("com.mysql.jdbc.Driver");
//			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nse", "root", "root");
		}
		
		
		if (connection != null) {
			return connection;
		} else {
			try {
				Class.forName(className);
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","123");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return connection;
		}

	}

	public void executeStmt(java.sql.PreparedStatement ps) {

		try {
			int rowsAffected = ps.executeUpdate();
			System.out.println("rows" + rowsAffected);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	};

}
