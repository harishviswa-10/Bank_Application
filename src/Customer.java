
public class Customer {
	 private int custId;
	   private String accNo;
	   private String name;
	   private double balance;
	   private String encPwd;
	   public Customer(int custId,String accNo,String name,double balance,String encPwd){
		   this.custId=custId;
		   this.accNo=accNo;
		   this.name=name;
		   this.balance=balance;
		   this.encPwd=encPwd;
	   }
	   public void details(){
		   String s="Customer Id:"+custId+"\nAccount No:"+accNo+"\nName:"+name+"\nBalance:"+balance;
		   System.out.println(s);
	   }
	   public void setCustId(int a) {
		   custId=a;
	   }
	   public void setAccNo(String a) {
		   accNo=a;
	   }
	   public void setName(String a) {
		   name=a;
	   }
	   public void setBalance(double a) {
		   balance=a;
	   }
	   public void setEncPwd(String a) {
		   encPwd=a;
	   }
	   public int getCustId() {
		   return custId;
	   }
	   public String getAccNo() {
		   return accNo;
	   }
	   public String getName() {
		   return name;
	   }
	   public double getBalance() {
		   return balance;
	   }
	   public String getEncPwd() {
		   return encPwd;
	   }
}
