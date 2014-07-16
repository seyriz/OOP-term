package Term_201002484_console;
import java.util.*;
/**
 * @see empoloyee
 * @version 0.10
 * @author Lee, Han-Wool (kudnya@gmail.com)
 */
public class empoloyee {
	private String name, phone, address, deposite;
	private int position, ID;
	private long monthPay;
	private boolean onWork;
	protected dbCon DB;
	protected IOProc IO;
	protected long nowTime;
	protected EmpoloyeesInfo empoloyees;
	private GregorianCalendar calender;
	private boolean managePerm;
	private final int[] pays = {9000, 8000, 7000, 6000, 5210};
	empoloyee(){
		this.empoloyees = null;
		this.DB = null;
		this.nowTime = -1;
		this.calender = null;
	}
	empoloyee(String name, String phone, String address, String deposite, int ID, int position, boolean onWork, boolean managePerm){
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.deposite = deposite;
		this.ID = ID;
		this.position = position;
		this.onWork = onWork;
		this.managePerm = managePerm;
		this.DB = new dbCon();
		this.IO = new IOProc();
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
	public boolean isOnWork(int ID) {
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
	public boolean setOnWork(boolean work, int ID){
		if(work){
			String query = "UPDATE 'PhoneBook' SET onWork=1 WHERE ID="+Integer.toString(ID)+";";
			return DB.excute(query);
		}
		else{
			String query = "UPDATE 'PhoneBook' SET onWork=0 WHERE ID="+Integer.toString(ID)+";";
			return DB.excute(query);
		}
	}
	public void manage() {
		// TODO Auto-generated method stub
		
	}
	public boolean isManager(){
		return managePerm;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDeposite() {
		return deposite;
	}
	public void setDeposite(String deposite) {
		this.deposite = deposite;
	}
	public long getMonthPay() {
		return monthPay;
	}
	public void setMonthPay(long monthPay) {
		this.monthPay = monthPay;
	}
	public String getName() {
		return name;
	}
	public int getPosition() {
		return position;
	}
	public int getID() {
		return ID;
	}
	public boolean isManagePerm() {
		return managePerm;
	}
	public int[] getPays() {
		return pays;
	}

}
