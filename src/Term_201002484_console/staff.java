package Term_201002484_console;

import java.util.Scanner;

public class staff extends empoloyee{
	staff(String name, String phone, String address, String deposite, int ID, int position, boolean onWork, boolean managePerm){
		super(name, phone, address, deposite, ID, position, onWork, managePerm);
	}
	
	public void manage(){
		Scanner inp_empoloyee = new Scanner(System.in);
		for(;;){
			System.out.println("================ 관리 ================");
			System.out.print("1. 내 급여명세서 출력\n2. Exit\n");
			int sel = Integer.parseInt(inp_empoloyee.nextLine());
			switch(sel){
			case 1: 
				IO.managePay(this);
			case 2:
				return;
			default:
				System.out.println("Worng");
				continue;
			}
		}
	}
}
