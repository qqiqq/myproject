import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HiveClient {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    public static void main(String ...args){
        try{
            Class.forName(driverName);
        } catch(ClassNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        }

        Connection conn;
        try{
            conn = DriverManager.getConnection(
                    "jdbc:hive2://server4:2181/hive;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveServer2",
                    "root",
                    "1234");
            Statement stmt = conn.createStatement();
 //           String table_name = "stockAmzn";
            String table_name = "SaleDataDealRS";
            stmt.execute("drop table if exists " + table_name);
            stmt.execute("create external table " + table_name +
                    " (n1 int,n2 int, flag int)" +
                    " ROW FORMAT DELIMITED FIELDS TERMINATED BY ' ' " +
                    "tblproperties(\"skip.header.line.count\"=\"1\")");
/**            stmt.execute("create external table " + table_name +
                    " (dt String, open double,high double,low double,close double,adj double,vol double)" +
                    " ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' " +w
                    "tblproperties(\"skip.header.line.count\"=\"1\")");
 **/

            String qrySql = "show tables";
            System.out.println("Running query " + qrySql);
            ResultSet rs = stmt.executeQuery(qrySql);
            if(rs.next()){
                System.out.println(rs.getString(1));
            }

            qrySql = "describe " + table_name;
            System.out.println("Running query " + qrySql);
            rs = stmt.executeQuery(qrySql);
            while(rs.next()){
                System.out.println(rs.getString(1) + "\t" + rs.getString(2));
            }
 //           String filepath = "/user/root/AMZN.csv";
            String filepath = "/user/root/SaleDataDealRS.txt";
            qrySql = "load data inpath '" + filepath + "' into table "
                    + table_name;
            System.out.println("Executing query: " + qrySql);
            stmt.execute(qrySql);

            qrySql = "select * from " + table_name + " limit 5";
            System.out.println("Executing query: " + qrySql);
            rs = stmt.executeQuery(qrySql);
            while (rs.next()) {
                System.out.println(String.valueOf(rs.getInt(1)) + "\t" +
                        rs.getString(2));
            }

            qrySql = "select count(*) from " + table_name;
            System.out.println("Running: " + qrySql);
            rs = stmt.executeQuery(qrySql);
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch(SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}