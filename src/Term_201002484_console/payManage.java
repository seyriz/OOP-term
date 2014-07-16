package Term_201002484_console;
import java.util.*;
import java.io.*;

/**
 * @see empoloyee
 * @version 0.10
 * @author Lee, Han-Wool (kudnya@gmail.com)
 */
public class payManage {
	private static dbCon DB;
	private static IOProc IO = new IOProc();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			FileReader isExsistDB = new FileReader("201002484");
			String ID, Passwd;
			DB = new dbCon();
			Scanner inp = new Scanner(System.in);
			System.out.println("================ Login ================");
			System.out.print("ID : ");
			ID = inp.nextLine();
			System.out.print("PassWord : ");
			Passwd = inp.nextLine();
			Login(Integer.parseInt(ID), Passwd.hashCode());
		}catch(FileNotFoundException e){
			DB = new dbCon();
			Scanner inp = new Scanner(System.in);
			System.out.println("================ New Start ================");

			System.out.print("Your Name : ");
			String name = inp.nextLine();

			System.out.print("PassWord : ");
			String Passwd = inp.nextLine();

			System.out.print("Phone : ");
			String Phone = inp.nextLine();

			System.out.print("Address : ");
			String address = inp.nextLine();

			System.out.print("Position : ");
			int position = Integer.parseInt(inp.nextLine());

			System.out.print("Deposite : ");
			String deposite = inp.nextLine();

			DB.newStart(Passwd , name, Phone, address, deposite, position);

			System.out.println("Your ID : 1");
			System.out.println("Your PassWord : "+Passwd);
			Login(1, Passwd.hashCode());
		}
		return;
	}
	/**
	 * 로그인하고 기본화면을 출력.
	 * @param ID 사원번호
	 * @param HASH HASH화 된 비밀번호
	 */
	public static void Login(int ID, int HASH){
		EmpoloyeesInfo logined = DB.Login(ID, HASH);
		if(logined!=null){
			IO.firstMenu(logined.getEmpoloyees());
		}
		else return;
	}

}
