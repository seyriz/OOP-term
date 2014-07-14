package Term_201002484_console;
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
	manager(EmpoloyeeStruct empoloyee){
		super(empoloyee);

	}
	/**
	 * 관리자 화면 
	 */
	public void manage(){
		Scanner inp_manager = new Scanner(System.in);
		for(;;){
			System.out.println("================ 관리 ================");
			System.out.print("1. 직원 관리\n2. 근태 관리\n3. Exit\ninp_managerut :\n");
			int sel = inp_manager.nextInt();
			switch(sel){
			case 1: // 직원 관리
				System.out.print("1. 한명씩 추가 \n2. 여러명 추가 \n3. 직원 퇴사\n4. exit\ninp_managerut :\n");
				sel = inp_manager.nextInt();
				switch(sel){

				case 1: // 1명씩 
					System.out.print("암호 : \n");
					String Passwd = inp_manager.nextLine();
					System.out.print("이름 : \n");
					String Name = inp_manager.nextLine();
					System.out.print("전화번호 : \n");
					String Phone = inp_manager.nextLine();
					System.out.print("주소 : \n");
					String Address = inp_manager.nextLine();
					System.out.print("계좌번호 : \n");
					String deposite = inp_manager.nextLine();
					System.out.print("직급 : \n");
					int position = inp_manager.nextInt();
					if(DB.addStaff(Passwd, Name, Phone, Address, deposite, position)){
						System.out.println("직원 추가에 성공했습니다. "+Name+"의 ID는 "+DB.select("PhoneBook","name", Name, "ID")+"입니다.");
					}
					else System.out.println("직원 추가에 실패하였습니다.");
					break;

				case 2: // 여러명
					for(;;){
						System.out.print("직원 명부 파일의 절대주소를 넣어주세요.(예 : C:\\Empoloyees.csv :");
						String filePath = inp_manager.nextLine();
						if(filePath.equals("exit")){
							break;
						}
						if(filePath.isEmpty()||!filePath.split(".").toString().equals("csv")){
							System.out.println("잘못된 파일 형식입니다.");
							continue;
						}
						else{
							DB.importEmpoloyee(importEmpoloyee(filePath));
							break;
						}
					}
					break;

				case 3: // 직원 퇴사
					System.out.print("퇴사한 직원의 사번 : \n");
					int ID = inp_manager.nextInt();
					DB.delStaff(ID);
					break;

				default:
					inp_manager.close();
					return;
				}

			case 2: // 직원 근태관리

				break;
			case 3:
				inp_manager.close();
				return;
			default:
				inp_manager.close();
				return;
			}
		}
	}
	/**
	 * 다수의 사원에 대한 급여 기록부를 출력
	 * @author seyriz
	 * @exception IOException
	 */
	public void printPaySheet(){
		Scanner inp = new Scanner(System.in);
		System.out.print("from : ");
		int start = inp.nextInt();
		System.out.print("to : ");
		int end = inp.nextInt();
		System.out.print("when(YYYYMM) : ");
		int YYYYMM = inp.nextInt();
		EmpoloyeeStruct empoloyees = DB.getEmpoloyeesPayStruct(start, end, YYYYMM);
		try{
			CSVWriter writer = new CSVWriter(new FileWriter("EmpoloyeesPaySheet.csv"), '\t');
			for(;empoloyees.getNext()!=null;empoloyees=empoloyees.getNext()){
				String[] entries = {Integer.toString(empoloyees.getID()), empoloyees.getName(), 
						empoloyees.getPhone(), Long.toString(empoloyees.getMonthPay()), empoloyees.getDeposite()};
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
	 * @return EmpoloyeeStruct
	 * @exception IOException
	 * 
	 */
	public EmpoloyeeStruct importEmpoloyee(String filePath){
		try{
			EmpoloyeeStruct empoloyees;
			CSVReader reader = new CSVReader(new FileReader(filePath));
			String[] nextLine = reader.readNext();
			empoloyees = new EmpoloyeeStruct(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], Integer.parseInt(nextLine[5]));
			while((nextLine = reader.readNext()) != null){
				empoloyees.addEmpoloyee(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], Integer.parseInt(nextLine[4]));
			}
			reader.close();
			return empoloyees;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}

	}
}
