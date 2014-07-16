package Term_201002484_console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
/**
 * @see empoloyee
 * @version 0.10
 * @author Lee, Han-Wool (kudnya@gmail.com)
 */
public class manager extends empoloyee {
	manager(){

	}
	/**
	 * 관리자 생성자
	 * @param empoloyee 직원정보
	 */
	manager(String name, String phone, String address, String deposite, int ID, int position, boolean onWork, boolean managePerm){
		super(name, phone, address, deposite, ID, position, onWork, managePerm);
	}
	/**
	 * 관리자 화면 
	 */
	public void manage(){
		Scanner inp_manage = new Scanner(System.in);
		for(;;){
			System.out.println("================ 관리 ================");
			System.out.print("1. 직원 관리\n2. 급여 관리\ninput :\n");
			int sel = Integer.parseInt(inp_manage.nextLine());
			if(sel==1){ // 직원 관리
				IO.manageEmpoloyee(this);
				continue;
			}
			if(sel==2){
				IO.managePay(this);
				continue;
			}
			else{
				return;
			}
		}
	}
	/**
	 * 다수의 사원에 대한 급여 기록부를 출력
	 * @author seyriz
	 * @exception IOException
	 */
}