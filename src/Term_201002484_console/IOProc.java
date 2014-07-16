package Term_201002484_console;
import java.io.*;
import java.util.Scanner;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
/**
 * IO 프로세싱 인터페이스
 * @version 0.10
 * @author Lee, Han-Wool (kudnya@gmail.com)
 */
public class IOProc {
	private dbCon DB;
	/**
	 * 개인의 한달 급여를 출력.
	 * @param ID 사원번호
	 * @param YYYYMM 연월(YYYYMM형식)
	 */
	IOProc(){
		this.DB = new dbCon();
	}
	public void firstMenu(empoloyee logined){
		for(;;){
			System.out.print("1. 출근 \n2. 퇴근\n3. 관리\n4. LogOut\nSelect : ");
			Scanner inp = new Scanner(System.in);
			int temp = Integer.parseInt(inp.nextLine());

			if(temp==1){
				logined.startWork(logined.getID(), System.currentTimeMillis()/1000);
				continue;
			}
			if(temp==2){
				logined.endWork(logined.getID(), System.currentTimeMillis()/1000);
				continue;
			}
			if(temp==3){
				logined.manage();
				continue;
			}
			if(temp==4){
				return;
			}
			else{
				System.out.println("Worng.");
				continue;
			}
		}
	}
	
	public void managePay(empoloyee logined){
		if(logined.isManager()){
			for(;;){
				Scanner inp_managePay = new Scanner(System.in);
				System.out.println("1. 특정직원 급여 보기\n2. 다수직원 급여 출력\n3. exit\n");
				try{
					int sel = Integer.parseInt(inp_managePay.nextLine());

					if(sel==1){
						System.out.print("출력하고 싶은 직원의 사원번호를 입력하세요 : ");
						int ID = Integer.parseInt(inp_managePay.nextLine());
						System.out.print("출력하고 싶은 달을 입력하세요(YYYYMM형식) : ");
						int YYYYMM = Integer.parseInt(inp_managePay.nextLine());
						printPayBill(ID, YYYYMM);
						continue;
					}
					if(sel==2){
						System.out.print("출력할 사원 (시작) : ");
						int start = Integer.parseInt(inp_managePay.nextLine());
						System.out.print("출력할 사원 (끝) : ");
						int end = Integer.parseInt(inp_managePay.nextLine());
						System.out.print("출력하고 싶은 달을 입력하세요(YYYYMM형식) : ");
						int YYYYMM = Integer.parseInt(inp_managePay.nextLine());
						printPaySheet(start, end, YYYYMM);
						continue;
					}
					if(sel==3){
						return;
					}
					else{
						continue;
					}
				}catch(NumberFormatException e){
					e.printStackTrace();
					return;
				}
			}
		}
		else{
			Scanner inp_empoloyee = new Scanner(System.in);
			System.out.print("출력하고 싶은 달을 입력하세요(YYYYMM형식) : ");
			int YYYYMM = Integer.parseInt(inp_empoloyee.nextLine());
			printPayBill(logined.getID(), YYYYMM);
			return;
		}
	}
	public void printPaySheet(int start, int end, int YYYYMM){
		EmpoloyeesInfo empoloyees = DB.getEmpoloyeesPayStruct(start, end, YYYYMM);
		try{
			CSVWriter writer = new CSVWriter(new FileWriter("EmpoloyeesPaySheet-"+YYYYMM+".csv"), ',');
			String[] entries = {"사원번호","이름","전화번호","급여","계좌번호"};
			writer.writeNext(entries);
			for(;empoloyees.getNext()!=null;empoloyees=empoloyees.getNext()){
				entries[0] = Integer.toString(empoloyees.getEmpoloyees().getID());
				entries[1] = empoloyees.getEmpoloyees().getName();
				entries[2] = empoloyees.getEmpoloyees().getPhone();
				entries[3] = Long.toString(empoloyees.getEmpoloyees().getMonthPay());
				entries[4] = empoloyees.getEmpoloyees().getDeposite();
				writer.writeNext(entries);
			}
			writer.close();
		}catch(IOException e){
			e.printStackTrace();

		}
	}
	/**
	 * CSV파일에서 여러 사원을 추가하기 위한 사원정보 링크드 리스트 생성 메소드
	 * @author seyriz
	 * @param filePath CSV파일 위치
	 * @return EmpoloyeesInfo
	 * @exception IOException
	 * 
	 */
	public EmpoloyeesInfo importEmpoloyee(String filePath){
		try{
			EmpoloyeesInfo empoloyees;
			CSVReader reader = new CSVReader(new FileReader(filePath), ',');
			reader.readNext();
			String[] nextLine = reader.readNext();
			empoloyees = new EmpoloyeesInfo(new empoloyee(nextLine[1], nextLine[2], nextLine[3], nextLine[4], 0,Integer.parseInt(nextLine[5]),false,false),nextLine[0]);
			EmpoloyeesInfo ret = empoloyees;
			while((nextLine = reader.readNext()) != null){
				empoloyees.addEmpoloyee(nextLine[0],new empoloyee(nextLine[1], nextLine[2], nextLine[3], nextLine[4], 0,Integer.parseInt(nextLine[5]),false,false));
				empoloyees = empoloyees.getNext();
			}
			reader.close();
			return ret;
		}catch(FileNotFoundException e){
			e.printStackTrace();
			return null;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}

	}
	public void manageEmpoloyee(empoloyee manager){
		if(manager.isManager()){
		Scanner inp_manageEmpoloyee = new Scanner(System.in);
		for(;;){
			System.out.print("1. 한명씩 추가 \n2. 여러명 추가 \n3. 직원 퇴사\n4. exit\ninput :\n");
			try{
				int sel = Integer.parseInt(inp_manageEmpoloyee.nextLine());
				if(sel==1) {// 1명씩 
					System.out.print("암호 : \n");
					String Passwd = inp_manageEmpoloyee.nextLine();
					System.out.print("이름 : \n");
					String Name = inp_manageEmpoloyee.nextLine();
					System.out.print("전화번호 : \n");
					String Phone = inp_manageEmpoloyee.nextLine();
					System.out.print("주소 : \n");
					String Address = inp_manageEmpoloyee.nextLine();
					System.out.print("계좌번호 : \n");
					String deposite = inp_manageEmpoloyee.nextLine();
					System.out.print("직급 : \n");
					int position = Integer.parseInt(inp_manageEmpoloyee.nextLine());
					if(DB.addStaff(Passwd, Name, Phone, Address, deposite, position)){
						System.out.println("직원 추가에 성공했습니다. "+Name+"의 ID는 "+DB.select("PhoneBook","name", Name, "ID")+"입니다.");
					}
					else System.out.println("직원 추가에 실패하였습니다.");
					continue;
				}
				if(sel==2){ // 여러명
					for(;;){
						System.out.print("직원 명부 파일의 절대주소를 넣어주세요.(예 : C:\\Empoloyees.csv :");
						String filePath = inp_manageEmpoloyee.nextLine();
						if(filePath.equals("exit")){
							break;
						}
						
						if(filePath.isEmpty()){
							System.out.println("잘못된 파일 형식입니다.");
							continue;
						}
						if(filePath.equals("sample")){
							DB.importEmpoloyee(importEmpoloyee("ImportSample.csv"));
							break;
						}
						else{
							DB.importEmpoloyee(importEmpoloyee(filePath));
							break;
						}
					}
					continue;
				}
				if(sel==3){ // 직원 퇴사
					System.out.print("퇴사한 직원의 사번 : \n");
					int ID = Integer.parseInt(inp_manageEmpoloyee.nextLine());
					if(DB.delStaff(ID)){
						System.out.println("직원 삭제에 성공 하였습니다.");
					}
					else System.out.println("직원 삭제에 실패 하였습니다.");
					continue;
				}
				if(sel==4){
					return;
				}
				else{
					continue;
				}
			}catch(NumberFormatException e){
				e.printStackTrace();
				return;
			}
		}
		}
		else{
			System.out.println("403 Permission Denied");
			return;
		}
	}

	public void printPayBill(int ID, int YYYYMM) {
		// TODO Auto-generated method stub
		System.out.println(DB.select("PhoneBook", "ID", Integer.toString(ID), "name")+"님의 "+YYYYMM+" 급여는 "+DB.getMonthPay(ID, YYYYMM)+"입니다.");
	}
}
