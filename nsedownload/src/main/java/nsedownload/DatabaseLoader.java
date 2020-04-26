package nsedownload;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseLoader {


    public static boolean loadReportToDB(String fileName) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        Connection connection = DBUtil.getConnection("MYSQL");

        fileName = "'" + fileName + "'";
        String sql = "copy NSE_DEMO FROM " + fileName + " DELIMITER ',' CSV header";
        try {
            CopyManager copyManager = new CopyManager((BaseConnection) connection);
            System.out.println("query FNO" + sql);
            copyManager.copyIn(sql);
            return true;
        } catch (PSQLException pe) {
            pe.printStackTrace();
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }


    public static void sqlLoad(String fileName) {

        try (Connection connection = DBUtil.getConnection("MYSQL")) {
            fileName = fileName.replace(".zip","").replace("//","/");
            //String loadQuery = "LOAD DATA LOCAL INFILE '" + "C:\\upload.csv" + "' INTO TABLE txn_tbl FIELDS TERMINATED BY ','" + " LINES TERMINATED BY '\n' (txn_amount, card_number, terminal_id) ";
           String loadQuery = "LOAD DATA LOCAL INFILE '"+fileName+"' INTO TABLE nsefobhav " +
                   "FIELDS TERMINATED BY ',' LINES TERMINATED BY '\\n' IGNORE 1 LINES " +
                   "(INSTRUMENT,SYMBOL,@var_EXPIRY_DT,STRIKE_PR,OPTION_TYP,OPEN,HIGH,LOW,CLOSE,SETTLE_PR,CONTRACTS,VAL_INLAKH,OPEN_INT,\tCHG_IN_OI,@var_TIMESTAMP)" +
                   "set EXPIRY_DT=STR_TO_DATE(@var_EXPIRY_DT,'%d-%b-%y'), TIMESTAMP=STR_TO_DATE(@var_TIMESTAMP,'%d-%b-%y');";
           System.out.println(loadQuery);
            Statement stmt = connection.createStatement();
            System.out.println("X:"+stmt.execute(loadQuery));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
