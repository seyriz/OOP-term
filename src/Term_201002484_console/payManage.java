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
			inp.close();
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
			int position = inp.nextInt();
			
			System.out.print("Deposite : ");
			String deposite = inp.nextLine();
			
			DB.newStart(Passwd , name, Phone, address, deposite, position);
			
			System.out.println("Your ID : 1");
			System.out.println("Your PassWord : "+Passwd);
			Login(1, Passwd.hashCode());
			inp.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return;
	}
	/**
	 * 로그인하고 기본화면을 출력.
	 * @param ID 사원번호
	 * @param HASH HASH화 된 비밀번호
	 */
	public static void Login(int ID, int HASH){
		empoloyee logined = DB.Login(ID, HASH);
			System.out.print("1. 출근 \n2. 퇴근\n3. 관리\n4. LogOut\nSelect : ");
			Scanner inp = new Scanner(System.in);
			int temp = inp.nextInt();
			if(temp==1){
				logined.startWork(ID, System.currentTimeMillis()/1000);
			}
			if(temp==2){
				logined.endWork(ID, System.currentTimeMillis()/1000);
			}
			if(temp==3){
				logined.manage();
			}
			if(temp==4){
				inp.close();
			}
			else{
				System.out.println("Worng.");
			}
		}
	
}
