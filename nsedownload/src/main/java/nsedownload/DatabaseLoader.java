package nsedownload;

import java.sql.Connection;
import java.sql.SQLException;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.postgresql.util.PSQLException;


public class DatabaseLoader {

	
	public static boolean loadReportToDB(String fileName) {
		// TODO Auto-generated method stub
		Connection connection = DBUtil.getConnection("POSTGRESS");

		fileName = "'"+fileName+"'";
		String sql = "copy NSE_DEMO FROM " + fileName + " DELIMITER ',' CSV header";
		try {
			CopyManager copyManager = new CopyManager((BaseConnection) connection);
			System.out.println("query FNO"+sql);
			copyManager.copyIn(sql);
				return true;
		} catch (PSQLException pe) {
			pe.printStackTrace();
			return true;
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
}
