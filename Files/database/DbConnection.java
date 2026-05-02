package database;
import java.sql.*;
public class DbConnection{

private final static String username="chinmay";
private final static String password="mysql123";
private static final String URL = "jdbc:mysql://localhost:3306/complaint_management_system?useSSL=false&allowPublicKeyRetrieval=true";
 public static Connection getconnection() throws SQLException{
try{
   Class.forName("com.mysql.cj.jdbc.Driver");
         return DriverManager.getConnection(URL,username,password);
    }
    catch(SQLException e)
    {
        throw  e;
    }
    catch (ClassNotFoundException e) {
    throw new SQLException("MySQL Driver not found in classpath!");
}
}
}