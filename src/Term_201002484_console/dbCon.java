package Term_201002484_console;
import java.io.*;
import java.sql.*;
import java.util.*;

import org.sqlite.*;
/**
 * @see empoloyee
 * @version 0.10
 * @author Lee, Han-Wool (kudnya@gmail.com)
 */
public class dbCon {
	private Connection dbConn;
	private String dbFileName = "201002484";
	private ResultSet dbResult;
	private Statement dbStat;
	private boolean isOpened = false;
	private final String DATABASE = "oo-term";
	private final int isManager = 2;
	static{
		try{
			Class.forName("org.sqlite.JDBC");
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	dbCon(){
	}
	/**
	 * DC Connection 생성자 
	 * @author seyriz
	 * @param dbFileName 데이터베이스 파일 위치
	 */
	dbCon(String dbFileName){
		this.dbFileName = dbFileName;
	}
	/**
	 * 데이터베이스 오픈 
	 * @author seyriz
	 * @exception SQLException
	 */
	public boolean open(){
		if(this.isOpened){
			System.out.println("DB already opened!!!!!");
			return false;
		}
		else{
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
			System.out.println("[DBG] Open");
			return this.isOpened;
		}
	}
	/**
	 * 데이터베이스 클로즈
	 * @author seyriz
	 * @exception SQLException
	 * @return
	 */
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
		System.out.println("[DBG] Close");
		return true;
	}
	/**
	 * 로그인 메소드
	 * @param ID 사원번호
	 * @param HASH HASH화 된 비밀번호
	 * @return 관리자 등급이면 manager, 아니면 empoloyee
	 */
	public EmpoloyeesInfo Login(int ID, int HASH){
		String query;
		EmpoloyeesInfo temp;
		open();
		try{
			query = "SELECT * FROM PhoneBook WHERE ID="+ID+";";
			this.dbResult = this.dbStat.executeQuery(query);
			if(this.dbResult.getInt("password")==HASH){
				if(this.dbResult.getBoolean("managePerm")){
					temp = new EmpoloyeesInfo(new manager(this.dbResult.getString("name"), this.dbResult.getString("phone"), 
							this.dbResult.getString("address"),this.dbResult.getString("deposite"), this.dbResult.getInt("ID"), 
							this.dbResult.getInt("position"), this.dbResult.getBoolean("onWork"),this.dbResult.getBoolean("managePerm")));
					this.dbResult.close();
					close();
					return temp;
				}
				else{
					temp = new EmpoloyeesInfo(new staff(this.dbResult.getString("name"), this.dbResult.getString("phone"), 
							this.dbResult.getString("address"),this.dbResult.getString("deposite"), this.dbResult.getInt("ID"), 
							this.dbResult.getInt("position"), this.dbResult.getBoolean("onWork"),this.dbResult.getBoolean("managePerm")));
					this.dbResult.close();
					close();
					return temp;
				}
			}
			else{
				System.out.println("Login failed. \n Check your account.");
				return null;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * SELECT문을 위한 메소드
	 * @param Table 테이블명 
	 * @param column 컬럼이름 
	 * @param where 검색조건
	 * @param what 반환조건 
	 * @return
	 */
	public String select(String Table, String column, String where, String what){
		String ret,query;
		open();
		try{
			query = "SELECT * FROM "+Table+" WHERE "+column+"='"+where+"';";
			this.dbResult = this.dbStat.executeQuery(query);
			ret = this.dbResult.getString(what);
			this.dbResult.close();
			close();
			return ret;
		}catch(SQLException e){
			e.printStackTrace();
			close();
			return null;
		}
	}
	/**
	 * SQL문 실행 메소드
	 * @param querys 쿼리문
	 * @return 실행결과
	 */
	public boolean excute(String querys){
		String query_prefix = querys.split(" ")[0];
		open();
		try{
			if(query_prefix.equals("CREATE")||query_prefix.equals("INSERT")||query_prefix.equals("UPDATE")||
					query_prefix.equals("DELETE")||query_prefix.equals("PROGMA")||query_prefix.equals("DELETE")){
				this.dbStat.executeUpdate(querys);
				close();
				return true;
			}
			else return false;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 새로운 시작
	 * @param Passwd 관리자 비밀번호
	 * @param Name 관리자 이름
	 * @param Phone 관리자 전화번호
	 * @param Address 관리자 주소
	 * @param deposite 관리자 계좌번호
	 * @param position 관지가 직급
	 */
	public void newStart(String Passwd, String Name, String Phone, String Address,String deposite, int position){
		String query;
		query = "CREATE TABLE 'PhoneBook' ('ID'	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,'password' INTEGER NOT NULL,'name' TEXT NOT NULL,'phone'	TEXT,'address'	TEXT,'position'	INTEGER NOT NULL,'deposite' TEXT NOT NULL,'onWork'	INTEGER NOT NULL,'managePerm' INTEGER NOT NULL)";
		excute(query);
		query = "CREATE TABLE 'Working' (	"
				+ "'ID'	TEXT NOT NULL,"
				+ "'YYYYMM'		INTEGER,"
				+ "'startWork'	INTEGER,"
				+ "'endWork'	INTEGER,"
				+ "'toDaysPay' 	INTEGER);";
		excute(query);
		query = "INSERT INTO 'PhoneBook'('ID','password','name','phone','address','position','deposite','onWork','managePerm') VALUES (NULL,"+Passwd.hashCode()+",'"+Name+"','"+Phone+"','"+Address+"',"+position+",'"+deposite+"',0,1)";
		excute(query);
	}
	/**
	 * 직원 대량 추가 
	 * @param empoloyees 직원정보
	 * @return 수행결과
	 */
	public boolean importEmpoloyee(EmpoloyeesInfo empoloyees){
		try{
			for(;empoloyees.getNext()!=null;empoloyees=empoloyees.getNext()){
				if(addStaff(empoloyees.getPassWd(), empoloyees.getEmpoloyees().getName(), empoloyees.getEmpoloyees().getPhone(), empoloyees.getEmpoloyees().getAddress(), empoloyees.getEmpoloyees().getDeposite(), empoloyees.getEmpoloyees().getPosition())){
					System.out.println("직원 추가에 성공했습니다. "+empoloyees.getEmpoloyees().getName()+"의 ID는 "+select("PhoneBook","name", empoloyees.getEmpoloyees().getName(), "ID")+"입니다.");
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
	/**
	 * 직원 추가
	 * @param Passwd 비밀번호 
	 * @param Name 이름 
	 * @param Phone 전화번호 
	 * @param Address 주소 
	 * @param deposite 계좌번호
	 * @param position 직급
	 * @return 수행결과 
	 */
	public boolean addStaff(String Passwd, String Name, String Phone, String Address, String deposite, int position){
		// TODO Debug DB Lock error!!!
		int perm;
		if(position<3){
			perm = 1;
		}
		else perm = 0;
		String querys=  "INSERT INTO 'PhoneBook'('ID','password','name','phone','address','position','deposite','onWork','managePerm') "
				+ "VALUES (NULL,"+Passwd.hashCode()+",'"+Name+"','"+Phone+"','"+Address+"',"+position+",'"+deposite+"',0,'"+perm+"');";
		try{
			open();
			this.dbStat.executeUpdate(querys);
			close();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		//		if(excute(querys)){
		//			return true;
		//		}
		//		else return false;
	}
	/**
	 * 직원 삭제
	 * @param ID 사원번호
	 * @return 수행결과 
	 */
	public boolean delStaff(int ID){
		String query = "DELETE FROM `PhoneBook` WHERE ID="+ID+";";
		if(excute(query)){
			return true;
		}
		else return false;
	}
	/**
	 * 다수 직원의 월급 조회 결과를 CSV로 출력 
	 * 에러에 취얀한 구조이지만 라이브러리에서 ResultSet의 오픈 여부를 알려주지 않아 처리할 수 없음
	 * @param start 사원번호 시작
	 * @param end 사원번호 끝
	 * @param YYYYMM 연월(YYYYMM형식)
	 * @return
	 */
	public EmpoloyeesInfo getEmpoloyeesPayStruct(int start, int end, int YYYYMM){
		EmpoloyeesInfo empoloyees=null;
		String name, phone, address, deposite;
		long monthPay;
		for(int ID = start; ID<=end; ID++){
			try{
				open();
				String query = "SELECT * FROM PhoneBook WHERE ID='"+ID+"';";
				this.dbResult = this.dbStat.executeQuery(query);
				name = this.dbResult.getString("name");
				phone = this.dbResult.getString("phone");
				address = this.dbResult.getString("address");
				deposite = this.dbResult.getString("deposite");
				this.dbResult.close();
				close();
				monthPay = getMonthPay(ID, YYYYMM);
				if(empoloyees==null){
					empoloyees = new EmpoloyeesInfo(new empoloyee(name, phone, address, deposite, ID, 0, false, false));
				}
				else{
					empoloyees.addEmpoloyee(new empoloyee(name, phone, address, deposite, ID, 0, false, false));
				}
			}catch(SQLException e){
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			finally{

			}
		}
		return empoloyees;
	}
	/**
	 * 한 직원 개인의 급여 조회
	 * @param ID 사원번호
	 * @param YYYYMM 연월(YYYYMM형식)
	 * @return 급여
	 */
	public long getMonthPay(int ID, int YYYYMM){
		String query = "SELECT SUM(toDaysPay) FROM Working WHERE ID="+ID+" AND YYYYMM="+YYYYMM+";";
		open();
		try{
			this.dbResult = this.dbStat.executeQuery(query);
			long ret = this.dbResult.getLong("SUM(toDaysPay)");
			this.dbResult.close();
			close();
			return ret;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}

}
