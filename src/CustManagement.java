import java.sql.*;
import java.util.*;
public class CustManagement {
	Scanner sc=new Scanner(System.in);
	List<Customer> list=new ArrayList<Customer>();
	DB d=new DB();
	int size=0;
	Connection con=d.getConnection();
    public void add(){
    	try{
    	  PreparedStatement ps=con.prepareStatement("select * from cust_details");
		  ResultSet rs=ps.executeQuery();
		  while(rs.next()){
			 Customer cust=new Customer(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getString(5));
			 list.add(cust);
			 size++;
		  }
    	}
    	catch(Exception e){
    		System.out.println(e);
    	}
    }
    public void addCustomer(){
    	  System.out.println("Enter name:");
		  String name=sc.nextLine();
		  String pwd;
		  while(true){
		    System.out.println("Enter password:");
		    pwd=sc.next();
		    if(check_pwd(pwd)) break;
		    else System.out.println("Password should contain atleast 1 upper-case,1 lowercase and 1 number");
	      }
		  System.out.println("Re-enter password:");
		  String rpwd=sc.next();
		  sc.nextLine();
		  if(!pwd.equals(rpwd)) System.out.println("Password does not match");
		  else {
			  size++;
			  String acc=size+"0"+size;
			try{
				PreparedStatement ps=con.prepareStatement("insert into cust_details values(?,?,?,?,?)");
				ps.setInt(1,size);
				ps.setString(2,acc);
				ps.setString(3,name);
				ps.setDouble(4,0.0);
				ps.setString(5,encrypt(pwd));
				ps.executeUpdate();
				Customer cust=new Customer(size,acc,name,0.0,encrypt(pwd));
				list.add(cust);
				System.out.println("Account created succesfully.");
				display(size);
			}
			catch(Exception e){
				System.out.println(e);
			}
		  }
    }
    public void display(int id){
    	for(Customer c:list){
    		if(id==c.getCustId()){
    			c.details();
    			break;
    		}
    	}
    }
    public void findCustomer(){
    	 int id=0;
    	 while(true){
    	  System.out.println("Enter name:");
		  String name=sc.nextLine();
		  System.out.println("Enter password:");
		  String pwd=sc.next();
		  String encpwd=encrypt(pwd);
		  for(Customer c:list){
			  if(name.equals(c.getName()) && encpwd.equals(c.getEncPwd())){
				 System.out.println("Welcome "+name+"!!!");
				 id=c.getCustId();
				 display(id);
				 break;
			  }
		  }
		  if(id!=0) break;
		  else System.out.println("Name and Password doesn't match");
		  sc.nextLine();
    	 }
    	 while(true){
    		 System.out.println("\nEnter 1 to Deposit,2 to Withdraw,3 to Money Transfer,4 to Change Password,0 to EXIT:");
    		 int n=sc.nextInt();
    		 if(n==1)         deposit(id);
    		 else if(n==2)    withdraw(id);
    		 else if(n==3)    transfer(id);
    		 else if(n==4)    changePwd(id);
    		 else break;
    	 }
    }
    public void deposit(int id){
    	System.out.println("Enter amount to deposit:");
		double d=sc.nextDouble();
		double balance=0;
		for(Customer c:list){
			if(id==c.getCustId()){
				balance=c.getBalance()+d;
				c.setBalance(balance);
				try{
				  PreparedStatement ps=con.prepareStatement("update cust_details set Balance=? where CustId=?");
				  ps.setDouble(1,balance);
				  ps.setInt(2, id);
				  ps.executeUpdate();
				  System.out.println("Amount deposited successfully.");
				}
				catch(Exception e){
					System.out.println(e);
				}
				break;
			}
		}	
    }
    public void withdraw(int id){
    	double balance=0,d;
    	while(true){
    		int check=0;
    	   System.out.println("Enter amount to withdraw:");
		   d=sc.nextDouble();
		   for(Customer c:list){
			if(id==c.getCustId() && c.getBalance()>=d){
				balance=c.getBalance()-d;
				c.setBalance(balance);
				check=1;
				try{
				  PreparedStatement ps=con.prepareStatement("update cust_details set Balance=? where CustId=?");
				  ps.setDouble(1,balance);
				  ps.setInt(2, id);
				  ps.executeUpdate();
				  System.out.println("Amount withdrawn successfully.");
				}
			    catch(Exception e){
				  System.out.println(e);
				}
				break;
			}
		   }
		   if(check==1) break;
		   else System.out.println("Insufficient Balance.");
    	}
    }
    public void transfer(int id){
      double bal1=0,bal2=0,amt=0;
      String acc="";
      while(true){
    	int flag=0;
    	System.out.println("Enter amount to transfer:");
    	amt=sc.nextDouble();
    	for(Customer c:list){
    		if(id==c.getCustId()){
    			if(c.getBalance()>=amt){
    				bal1=c.getBalance()-amt;
    				c.setBalance(bal1);
    				flag=1;
    			}
    			else{
    				System.out.println("Insufficient Balance");
    				break;
    			}
    		}
    	}
    	if(flag==1) break;
      }
      while(true){
    	int flag=0;
        System.out.println("Enter account number to transfer money to:");
  	    acc=sc.next();
  	    for(Customer c:list){
  	    	if(acc.equals(c.getAccNo())){
  	    		bal2=c.getBalance()+amt;
  	    		c.setBalance(bal2);
  	    		flag=1;
  	    		break;
  	    	}
  	    }
  	    if(flag==1) break;
  	    else System.out.println("Incorrect account number.");
      }
  	  try{
  		PreparedStatement ps=con.prepareStatement("update cust_details set Balance=? where CustId=?");
  		ps.setDouble(1, bal1);
  		ps.setInt(2, id);
  		ps.executeUpdate();
  		PreparedStatement p=con.prepareStatement("update cust_details set Balance=? where AccNo=?");
  		p.setDouble(1, bal2);
  		p.setString(2, acc);
  		p.executeUpdate();
  		System.out.println("Amount Transferred Succesfully");
  	  }
  	  catch(Exception e){
  		  System.out.println(e);
  	  }
    }
    public void changePwd(int id){
    	 while(true){
        	String pwd;
        	while(true){
        	  System.out.println("Enter new password:");
        	  pwd=sc.next();
        	  if(check_pwd(pwd)) break;
        	  else System.out.println("Password should contain atleast 1 upper-case,1 lowercase and 1 number");
        	}
        	System.out.println("Confirm new password:");
        	String rpwd=sc.next();
        	if(pwd.equals(rpwd)){
        		String password=encrypt(pwd);
        		for(Customer c:list){
        			if(id==c.getCustId()){
        				c.setEncPwd(password);
        				try{
        					PreparedStatement ps=con.prepareStatement("update cust_details set EncPwd=? where CustId=?");
        					ps.setString(1,password);
        					ps.setInt(2,id);
        					ps.executeUpdate();
        					System.out.println("Password changed successfully.");
        				}
        				catch(Exception e){
        					System.out.println(e);
        				}
        				break;
        			}
        		}
        		break;
        	}
        	else System.out.println("Password doesn't match.");
          }
    }
    public String encrypt(String pwd) {
		String s="";
		for(int i=0;i<pwd.length();i++) {
			char c=pwd.charAt(i);
			if(c=='z') c='a';
			else if(c=='Z') c='A';
			else if(c=='9') c='0';
			else c+=1;
			s+=c;
		}
		return s;
	}
    public boolean check_pwd(String s){
	    int u=0,l=0,n=0;
	    for(int i=0;i<s.length();i++){
	    	char c=s.charAt(i);
	    	if(Character.isLowerCase(c)) l++;
	    	else if(Character.isUpperCase(c)) u++;
	    	else n++;
	    }
	    if(u>=1 && l>=1 && n>=1)  return true;
	    return false;
	}
}
