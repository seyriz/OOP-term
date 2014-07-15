package Term_201002484_console;
import java.util.*;
/**
 * @see empoloyee
 * @version 0.10
 * @author Lee, Han-Wool (kudnya@gmail.com)
 */
public class empoloyee implements IOProc{
	EmpoloyeesInfo empoloyees;
	protected dbCon DB;
	protected long nowTime;
	private GregorianCalendar calender;
	private final int[] pays = {9000, 8000, 7000, 6000, 5210};
	empoloyee(){
		this.empoloyees = null;
		this.DB = null;
		this.nowTime = -1;
		this.calender = null;
	}
	empoloyee(EmpoloyeesInfo empoloyee){
		this.empoloyees = empoloyee;
		this.DB = new dbCon();
		this.calender = new GregorianCalendar();
	}
	/**
	 * 출근
	 * @param ID 사원번호 
	 * @param nowTime 현재시간(UNIX시간)
	 */
	public void startWork(int ID, long nowTime) {
		// TODO Auto-generated method stub
		String query;
		if(isOnWork(ID)){
			System.out.println("당신은 아직 일하고 있습니다.");
			return;
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
			DB.excute(query);
			setOnWork(true, ID);
			System.out.println("좋은 하루 되세요!");
			return;
		}
	}
	/**
	 * 퇴근
	 * @param ID 사원번호 
	 * @param nowTime 현재시간(UNIX시간)
	 */
	public void endWork(int ID, long nowTime) {
		// TODO Auto-generated method stub
		String query;
		if(!isOnWork(ID)){
			System.out.println("당신은 아직 출근도 안했습니다.");
			return;
		}
		else{
			int stWork = Integer.parseInt(DB.select("Working","ID",Integer.toString(ID),"startWork"));
			int position = Integer.parseInt(DB.select("PhoneBook","ID",Integer.toString(ID),"position"));
			int toDaysPay = (int)(((nowTime-stWork)/60/60)*this.pays[position]);
			query = "UPDATE 'Working' SET endWork="+Long.toString(nowTime)+" WHERE ID="+ID+";";
			DB.excute(query);
			query = "UPDATE 'Working' SET toDaysPay="+toDaysPay+" WHERE ID="+ID+";";
			DB.excute(query);
			setOnWork(false, ID);
			System.out.println("고생하셨습니다.");
			return;
		}
	}
	/**
	 * 근무중인지 확인
	 * @param ID 사원번호
	 * @return 근무 여부
	 */
	private boolean isOnWork(int ID) {
		if(DB.select("PhoneBook","ID",Integer.toString(ID),"onWork").equals(Integer.toString(1))){
			return true;
		}
		else return false;
	}
	/**
	 * 근무 여부를 셋
	 * @param work 근무 여부
	 * @param ID 사원번호
	 * @return 수행 결과 
	 */
	private boolean setOnWork(boolean work, int ID){
		if(work){
			String query = "UPDATE 'PhoneBook' SET onWork=1 WHERE ID="+Integer.toString(ID)+";";
			return DB.excute(query);
		}
		else{
			String query = "UPDATE 'PhoneBook' SET onWork=0 WHERE ID="+Integer.toString(ID)+";";
			return DB.excute(query);
		}
	}
	/**
	 * 개인 관리 화면
	 */
	public void manage() {
		// TODO Auto-generated method stub
		Scanner inp_empoloyee = new Scanner(System.in);
		for(;;){
			System.out.println("================ 관리 ================");
			System.out.print("1. 내 급여명세서 출력\n2. Exit\n");
			int sel = Integer.parseInt(inp_empoloyee.nextLine());
			switch(sel){
			case 1: 
				managePay();
			case 2:
				return;
			default:
				System.out.println("Worng");
				continue;
			}
		}

	}

	private void managePay(){
		Scanner inp_empoloyee = new Scanner(System.in);
		System.out.print("출력하고 싶은 달을 입력하세요(YYYYMM형식) : ");
		int YYYYMM = Integer.parseInt(inp_empoloyee.nextLine());
		printPayBill(this.empoloyees.getID(), YYYYMM);
		return;
	}

	public void printPayBill(int ID, int YYYYMM) {
		// TODO Auto-generated method stub
		System.out.println(this.empoloyees.getName()+"님의 "+YYYYMM+" 급여는 "+DB.getMonthPay(this.empoloyees.getID(), YYYYMM)+"입니다.");
	}
	public void printPaySheet(){
	}
	public EmpoloyeesInfo importEmpoloyee(String filePath) {
		// TODO Auto-generated method stub
		return null;
	}

	public void firstMenu(){
		for(;;){
			System.out.print("1. 출근 \n2. 퇴근\n3. 관리\n4. LogOut\nSelect : ");
			Scanner inp = new Scanner(System.in);
			int temp = Integer.parseInt(inp.nextLine());

			if(temp==1){
				startWork(this.empoloyees.getID(), System.currentTimeMillis()/1000);
				continue;
			}
			if(temp==2){
				endWork(this.empoloyees.getID(), System.currentTimeMillis()/1000);
				continue;
			}
			if(temp==3){
				manage();
				continue;
			}
			if(temp==4){
				continue;
			}
			else{
				System.out.println("Worng.");
				continue;
			}
		}
	}
}
