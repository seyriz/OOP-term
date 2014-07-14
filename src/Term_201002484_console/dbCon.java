package Term_201002484_console;
import java.io.*;
import java.sql.*;
import java.util.*;

import org.sqlite.*;

public class dbCon {
	private Connection dbConn;
	private String dbFileName = "201002484";
	private ResultSet dbResult;
	private Statement dbStat;
	private boolean isOpened = false;
	private final String DATABASE = "oo-term";
	private final int overManager = 2;
	private GregorianCalendar calender;
	private final int[] pays = {9000, 8000, 7000, 6000, 5210};

	static{
		try{
			Class.forName("org.sqlite.JDBC");
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	dbCon(){
		this.calender = new GregorianCalendar();
	}

	dbCon(String dbFileName){
		this.dbFileName = dbFileName;
		this.calender = new GregorianCalendar();
	}

	public boolean open(){
		try{
			SQLiteConfig conf = new SQLiteConfig();
			conf.setReadOnly(false);
			this.dbConn = DriverManager.getConnection("jdbc:sqlite:"+this.dbFileName,conf.toProperties());
			this.dbStat = dbConn.createStatement();
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		this.isOpened = true;
		System.out.println("DBG: DB opened");
		return this.isOpened;
	}

	public boolean close(){
		if(this.isOpened == false) { return true; }

		try {
			this.dbStat.close();
			this.dbConn.close();
		} catch(SQLException e){ 
			e.printStackTrace(); 
			return false; 
		}
		this.isOpened = false;
		System.out.println("DBG: DB closed");
		return true;
	}
	public empoloyee Login(int ID, int HASH){
		String query;
		empoloyee temp;
		open();
		try{
			query = "SELECT * FROM PhoneBook WHERE ID='"+Integer.toString(ID)+"' and password='"+Integer.toString(HASH)+"'";
			this.dbResult = this.dbStat.executeQuery(query);
			int position = this.dbResult.getInt("position");
			if(position>overManager){
				temp = new empoloyee(this.dbResult.getInt("ID"),this.dbResult.getString("name"),position,false);
				this.dbResult.close();
				close();
				return temp;
			}
			else{
				temp = new manager(this.dbResult.getInt("ID"),this.dbResult.getString("name"),position,false);
				this.dbResult.close();
				close();
				return temp;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public String select(String Table, String column, String where, String what){
		String ret,query;
		open();
		try{
			query = "SELECT * FROM "+Table+" WHERE "+column+"="+where+";";
			this.dbResult = this.dbStat.executeQuery(query);
			ret = this.dbResult.getString(what);
			this.dbResult.close();
			close();
			return ret;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	public boolean excute(String querys){
		String query_prefix = querys.split(" ")[0];
		open();
		try{
			if(query_prefix.equals("CREATE")){
				this.dbStat.executeUpdate(querys);
			}
			if(query_prefix.equals("INSERT")){
				this.dbStat.executeUpdate(querys);
			}
			if(query_prefix.equals("UPDATE")){
				this.dbStat.executeUpdate(querys);
			}
			if(query_prefix.equals("DELETE")){
				this.dbStat.executeUpdate(querys);
			}
			if(query_prefix.equals("PROGMA")){
				this.dbStat.executeUpdate(querys);
			}
			if(query_prefix.equals("DELETE")){
				this.dbStat.executeUpdate(querys);
			}
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		close();
		return true;
	}

	public void newStart(String Passwd, String Name, String Phone, String Address,String deposite, int position){
		String query,progma;
		progma = "PRAGMA foreign_keys = \"1\";";
		query = "CREATE TABLE 'PhoneBook' ('ID'	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,'password' INTEGER NOT NULL,'name' TEXT NOT NULL,'phone'	TEXT,'address'	TEXT,'position'	INTEGER NOT NULL,'deposite' TEXT NOT NULL,'onWork'	INTEGER NOT NULL)";
		excute(query);
		excute(progma);
		query = "CREATE TABLE 'Working' (	"
				+ "'ID'	TEXT NOT NULL,"
				+ "'YYYYMM'		INTEGER,"
				+ "'startWork'	INTEGER,"
				+ "'endWork'	INTEGER,"
				+ "'toDaysPay' 	INTEGER);";
		excute(query);
		query = "INSERT INTO 'PhoneBook'('ID','password','name','phone','address','position','deposite','onWork') VALUES (NULL,"+Passwd.hashCode()+",'"+Name+"','"+Phone+"','"+Address+"',"+position+",'"+deposite+"',0)";
		excute(query);
	}
	public boolean startWork(int ID, long nowTime) {
		// TODO Auto-generated method stub
		String query;
		if(isOnWork(ID)){
			System.out.println("당신은 아직 일하고 있습니다.");
			return false;
		}
		else{
			StringBuffer YYYYMM = new StringBuffer();
			YYYYMM.append(this.calender.get(this.calender.YEAR));
			int thisMonth;
			if(this.calender.get(this.calender.MONTH)<8){
				YYYYMM.append("0");
				thisMonth = this.calender.get(this.calender.MONTH);
				++thisMonth;
				YYYYMM.append(thisMonth);
			}
			else{
				thisMonth = this.calender.get(this.calender.MONTH);
				++thisMonth;
				YYYYMM.append(thisMonth);
			}
			query = "INSERT INTO 'Working'('ID','YYYYMM','startWork','endWork','toDaysPay') VALUES ('"+ID+"','"+YYYYMM+"',"+nowTime+",NULL,NULL);";
			excute(query);
			setOnWork(true, ID);
			return true;
		}
	}

	public boolean endWork(int ID, long nowTime) {
		// TODO Auto-generated method stub
		String query;
		if(!isOnWork(ID)){
			System.out.println("당신은 아직 출근도 안했습니다.");
			return false;
		}
		else{
			int stWork = Integer.parseInt(select("Working","ID",Integer.toString(ID),"startWork"));
			int position = Integer.parseInt(select("PhoneBook","ID",Integer.toString(ID),"position"));
			int toDaysPay = (int)(((nowTime-stWork)/60/60)*this.pays[position]);
			query = "UPDATE 'Working' SET endWork="+Long.toString(nowTime)+" WHERE ID="+ID+";";
			excute(query);
			query = "UPDATE 'Working' SET toDaysPay="+toDaysPay+" WHERE ID="+ID+";";
			excute(query);
			setOnWork(false, ID);
			return true;
		}
	}
	private boolean isOnWork(int ID) {
		if(select("PhoneBook","ID",Integer.toString(ID),"onWork").equals(Integer.toString(1))){
			return true;
		}
		else return false;
	}
	
	private boolean setOnWork(boolean work, int ID){
		if(work){
			String query = "UPDATE 'PhoneBook' SET onWork=1 WHERE ID="+Integer.toString(ID)+";";
			return excute(query);
		}
		else{
			String query = "UPDATE 'PhoneBook' SET onWork=0 WHERE ID="+Integer.toString(ID)+";";
			return excute(query);
		}
	}
	public boolean importEmpoloyee(EmpoloyeeStruct empoloyees){
		try{
			for(;empoloyees.getNext()!=null;empoloyees=empoloyees.getNext()){
				if(addStaff(empoloyees.getPasswWd(), empoloyees.getName(), empoloyees.getPhone(), empoloyees.getAddress(), empoloyees.getDeposite(), empoloyees.getPosition())){
					System.out.println("직원 추가에 성공했습니다. "+empoloyees.getName()+"의 ID는 "+select("PhoneBook","name", empoloyees.getName(), "ID")+"입니다.");
				}
				else{
					System.out.println("직원 추가에 실패하였습니다.");
				}
			}
			return true;
		}catch(NullPointerException e){
			e.printStackTrace();
			return false;
		}
	}
	public boolean addStaff(String Passwd, String Name, String Phone, String Address, String deposite, int position){
		String querys=  "INSERT INTO 'PhoneBook'('ID','password','name','phone','address','position','deposite','onWork') "
				+ "VALUES (NULL,"+Passwd.hashCode()+",'"+Name+"',"+Phone+","+Address+","+position+","+deposite+",0);";
		if(excute(querys)){
			return true;
		}
		else return false;
	}
	public boolean delStaff(int ID){
		String query = "DELETE FROM `PhoneBook` WHERE ID"+ID+";";
		if(excute(query)){
			return true;
		}
		else return false;
	}
	public EmpoloyeeStruct getEmpoloyeesPayStruct(int start, int end, int YYYYMM){
		try{
			open();
			String query = "SELECT * FROM PhoneBook";
			this.dbResult = this.dbStat.executeQuery(query);
			EmpoloyeeStruct empoloyees = null;					
			while(this.dbResult.next()){
				if(empoloyees==null){
					empoloyees = new EmpoloyeeStruct(this.dbResult.getString("ID"), this.dbResult.getString("phone"), this.dbResult.getString("address"), this.dbResult.getString("deposite"), this.dbResult.getInt("ID"), getMonthPay(this.dbResult.getInt("ID"), YYYYMM));
				}
				else{
					empoloyees.addEmpoloyee(this.dbResult.getString("ID"), this.dbResult.getString("phone"), this.dbResult.getString("address"), this.dbResult.getString("deposite"), this.dbResult.getInt("ID"), getMonthPay(this.dbResult.getInt("ID"), YYYYMM));
				}
			}
			this.dbResult.close();
			close();
			return empoloyees;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public long getMonthPay(int ID, int YYYYMM){
		String query = "SELECT SUM(toDaysPay) FROM Working WHERE ID="+ID+" AND YYYYMM="+YYYYMM+";";
		open();
		try{
			this.dbResult = this.dbStat.executeQuery(query);
			long ret = this.dbResult.getLong("SUM(toDaysPay)");
			this.dbResult.close();
			close();
			System.out.println("ID : "+ID+" "+select("PhoneBook","ID",Integer.toString(ID),"name")+"의 "+ YYYYMM +" 임금은 "+ret+"입니다.");
			return ret;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
}
