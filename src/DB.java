import java.sql.Connection;
import java.sql.DriverManager;


public class DB {
	public Connection getConnection(){
    	Connection con=null;
    	try{
    	 Class.forName("com.mysql.cj.jdbc.Driver");
    	 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
    	}
    	catch(Exception e){
    		System.out.println(e);
    	}
    	return con;
    }
}
