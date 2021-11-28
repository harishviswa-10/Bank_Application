import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		CustManagement cm=new CustManagement();
		cm.add();
		System.out.println("Welcome!");
		while(true){
			  System.out.println("Enter 1 for new customer,2 for customer already,0 to exit:");
			  int n=sc.nextInt();
			  if(n==1) {
				  cm.addCustomer();
			  }
			  else if(n==2){
				  cm.findCustomer();
			  }
			  else break;
		}	  
	}
}
