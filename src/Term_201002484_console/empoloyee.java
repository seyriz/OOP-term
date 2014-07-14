package Term_201002484_console;
import java.util.*;

public class empoloyee implements IOProc{
	private String name;
	private int ID,position;
	protected dbCon DB;
	protected long nowTime;
	empoloyee(){

	}
	empoloyee(int ID, String name, int position, boolean onWork){
		this.ID = ID;
		this.name = name;
		this.position = position;
		this.DB = new dbCon();
	}
	public boolean startWork(){
		this.nowTime = System.currentTimeMillis()/1000;
		if(DB.startWork(ID, nowTime)){
			System.out.println("즐거운 하루 되세요!");
			return true;
		}
		else return false;
	}
	
	public boolean endWork(){
		this.nowTime = System.currentTimeMillis()/1000;
		if(DB.endWork(ID, nowTime)){
			System.out.println("고생하셨습니다!");
			return true;
		}
		else return false;
	}

	// 내정보 관리 
	public void manage() {
		// TODO Auto-generated method stub
		Scanner inp_empoloyee = new Scanner(System.in);
		for(;;){
			System.out.println("================ 관리 ================");
			System.out.print("1. 내 급여명세서 출력\n2. Exit\n");
			int sel = inp_empoloyee.nextInt();
			switch(sel){
			case 1: 
				System.out.print("출력하고 싶은 달을 입력하세요(YYYYMM형식) : ");
				int YYYYMM = inp_empoloyee.nextInt();
				printPayBill(ID, YYYYMM);
				break;
			case 2:
				inp_empoloyee.close();
				return;
			default:
				System.out.println("Worng");
				continue;
			}
		}

	}

	// 내 급여명세서 출력
	public int getID() {
		return ID;
	}
	public String getName() {
		return name;
	}
	public int getPosition() {
		return position;
	}
	public void printPayBill(int ID, int YYYYMM) {
		// TODO Auto-generated method stub
		
	}
	public void printPaySheet(int YYYYMM){
		
	}
	public EmpoloyeeStruct importEmpoloyee(String filePath) {
		// TODO Auto-generated method stub
		return null;
	}

}
